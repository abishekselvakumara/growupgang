package com.gang.growup.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gang.growup.dto.ShareSettingsDTO;
import com.gang.growup.dto.SharedProfileDTO;
import com.gang.growup.entity.SharedProfile;
import com.gang.growup.entity.SharedProfileView;
import com.gang.growup.entity.User;
import com.gang.growup.exception.ResourceNotFoundException;
import com.gang.growup.repository.SharedProfileRepository;
import com.gang.growup.repository.SharedProfileViewRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gang.growup.entity.PlantStreak;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShareProfileService {
    
    private final SharedProfileRepository sharedProfileRepository;
    private final SharedProfileViewRepository sharedProfileViewRepository;
    private final ObjectMapper objectMapper;
    private final SecureRandom secureRandom = new SecureRandom();
    
    public ShareProfileService(
            SharedProfileRepository sharedProfileRepository,
            SharedProfileViewRepository sharedProfileViewRepository,
            ObjectMapper objectMapper) {
        this.sharedProfileRepository = sharedProfileRepository;
        this.sharedProfileViewRepository = sharedProfileViewRepository;
        this.objectMapper = objectMapper;
    }
    
    @Transactional
    public SharedProfileDTO createSharedProfile(User user, ShareSettingsDTO settings) {
        // Check if user already has a shared profile
        sharedProfileRepository.findByUser(user).ifPresent(profile -> {
            profile.setIsActive(false); // Deactivate old profile
            sharedProfileRepository.save(profile);
        });
        
        String shareCode = generateShareCode();
        String baseUrl = "https://growup.plus/profile/";
        String shareUrl = baseUrl + shareCode;
        
        String settingsJson;
        try {
            settingsJson = objectMapper.writeValueAsString(settings.toMap());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize settings", e);
        }
        
        SharedProfile sharedProfile = new SharedProfile(user, shareCode, shareUrl, settingsJson);
        
        // Set expiry based on settings
        if (!"never".equals(settings.getExpiryOption())) {
            LocalDateTime expiresAt = calculateExpiry(settings);
            sharedProfile.setExpiresAt(expiresAt);
        }
        
        SharedProfile saved = sharedProfileRepository.save(sharedProfile);
        return convertToDTO(saved);
    }
    
    @Transactional(readOnly = true)
    public SharedProfileDTO getSharedProfile(String shareCode) {
        SharedProfile profile = sharedProfileRepository.findByShareCode(shareCode)
                .orElseThrow(() -> new ResourceNotFoundException("Shared profile not found"));
        
        if (!profile.getIsActive()) {
            throw new ResourceNotFoundException("Profile is no longer active");
        }
        
        if (profile.getExpiresAt() != null && profile.getExpiresAt().isBefore(LocalDateTime.now())) {
            profile.setIsActive(false);
            sharedProfileRepository.save(profile);
            throw new ResourceNotFoundException("Profile has expired");
        }
        
        return convertToPublicDTO(profile);
    }
    
    @Transactional
    public void recordProfileView(String shareCode, String ipAddress, String userAgent) {
        SharedProfile profile = sharedProfileRepository.findByShareCode(shareCode)
                .orElseThrow(() -> new ResourceNotFoundException("Shared profile not found"));
        
        // Increment view count
        sharedProfileRepository.incrementViews(shareCode, LocalDateTime.now());
        
        // Record view details
        SharedProfileView view = new SharedProfileView(profile, ipAddress, userAgent);
        sharedProfileViewRepository.save(view);
    }
    
    @Transactional
    public SharedProfileDTO updateShareSettings(User user, ShareSettingsDTO settings) {
        SharedProfile profile = sharedProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Shared profile not found"));
        
        try {
            String settingsJson = objectMapper.writeValueAsString(settings.toMap());
            profile.setShareSettings(settingsJson);
            
            // Update expiry if changed
            if (!"never".equals(settings.getExpiryOption())) {
                profile.setExpiresAt(calculateExpiry(settings));
            } else {
                profile.setExpiresAt(null);
            }
            
            SharedProfile updated = sharedProfileRepository.save(profile);
            return convertToDTO(updated);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize settings", e);
        }
    }
    
    @Transactional
    public void deactivateSharedProfile(User user) {
        sharedProfileRepository.findByUser(user).ifPresent(profile -> {
            profile.setIsActive(false);
            sharedProfileRepository.save(profile);
        });
    }
    
    @Transactional
    public Map<String, Object> getShareAnalytics(User user) {
        SharedProfile profile = sharedProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Shared profile not found"));
        
        List<SharedProfileView> views = sharedProfileViewRepository.findBySharedProfile(profile);
        
        Map<String, Object> analytics = new HashMap<>();
        analytics.put("totalViews", profile.getViewsCount());
        analytics.put("lastViewedAt", profile.getLastViewedAt());
        analytics.put("createdAt", profile.getCreatedAt());
        analytics.put("isActive", profile.getIsActive());
        
        // Views by day (last 7 days)
        Map<String, Long> viewsByDay = new HashMap<>();
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        
        for (SharedProfileView view : views) {
            if (view.getViewedAt().isAfter(sevenDaysAgo)) {
                String dayKey = view.getViewedAt().toLocalDate().toString();
                viewsByDay.put(dayKey, viewsByDay.getOrDefault(dayKey, 0L) + 1);
            }
        }
        analytics.put("viewsByDay", viewsByDay);
        
        return analytics;
    }
    
    @Scheduled(cron = "0 0 0 * * *") // Run daily at midnight
    @Transactional
    public void deactivateExpiredProfiles() {
        LocalDateTime now = LocalDateTime.now();
        List<SharedProfile> expiredProfiles = sharedProfileRepository.findExpiredProfiles(now);
        
        for (SharedProfile profile : expiredProfiles) {
            profile.setIsActive(false);
            sharedProfileRepository.save(profile);
        }
    }
    
    private String generateShareCode() {
        byte[] bytes = new byte[16];
        secureRandom.nextBytes(bytes);
        String code = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        // Ensure uniqueness
        while (sharedProfileRepository.existsByShareCode(code)) {
            bytes = new byte[16];
            secureRandom.nextBytes(bytes);
            code = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        }
        return code;
    }
    
    private LocalDateTime calculateExpiry(ShareSettingsDTO settings) {
        LocalDateTime now = LocalDateTime.now();
        switch (settings.getExpiryOption()) {
            case "24h":
                return now.plusHours(24);
            case "7d":
                return now.plusDays(7);
            case "30d":
                return now.plusDays(30);
            case "custom":
                if (settings.getCustomExpiryDate() != null) {
                    return LocalDateTime.parse(settings.getCustomExpiryDate() + "T23:59:59");
                }
                return now.plusDays(30);
            default:
                return null;
        }
    }
    
    private SharedProfileDTO convertToDTO(SharedProfile profile) {
        SharedProfileDTO dto = new SharedProfileDTO();
        dto.setId(profile.getId());
        dto.setShareCode(profile.getShareCode());
        dto.setShareUrl(profile.getShareUrl());
        dto.setIsActive(profile.getIsActive());
        dto.setExpiresAt(profile.getExpiresAt());
        dto.setViewsCount(profile.getViewsCount());
        dto.setLastViewedAt(profile.getLastViewedAt());
        dto.setCreatedAt(profile.getCreatedAt());
        
        try {
            Map<String, Object> settings = objectMapper.readValue(profile.getShareSettings(), Map.class);
            dto.setShareSettings(settings);
        } catch (JsonProcessingException e) {
            dto.setShareSettings(new HashMap<>());
        }
        
        return dto;
    }
    
    private SharedProfileDTO convertToPublicDTO(SharedProfile profile) {
        SharedProfileDTO dto = new SharedProfileDTO();
        dto.setShareCode(profile.getShareCode());
        dto.setShareUrl(profile.getShareUrl());
        dto.setCreatedAt(profile.getCreatedAt());
        
        // Parse settings to know what to share
        Map<String, Object> settings;
        try {
            settings = objectMapper.readValue(profile.getShareSettings(), Map.class);
        } catch (JsonProcessingException e) {
            settings = new HashMap<>();
        }
        
        User user = profile.getUser();
        
        // Only include what's allowed by settings
        if (isTrue(settings, "shareName")) {
            dto.setFullName(user.getFullName());
        }
        
        if (isTrue(settings, "shareUsername")) {
            dto.setUsername(user.getUsername());
        }
        
        if (isTrue(settings, "shareProfilePicture")) {
            // Load profile picture from storage
            String profilePic = loadProfilePicture(user.getUsername());
            dto.setProfilePicture(profilePic);
        }
        
        if (isTrue(settings, "shareBio")) {
            //dto.setBio(user.getBio());
        }
        
        if (isTrue(settings, "shareLocation")) {
            //dto.setLocation(user.getLocation());
        }
        
        if (isTrue(settings, "shareWebsite")) {
            //dto.setWebsite(user.getWebsite());
        }
        
        if (isTrue(settings, "shareJoinDate")) {
            //dto.setJoinDate(user.getJoinDate());
        }
        
        // Build public stats
        Map<String, Object> publicStats = new HashMap<>();
        if (isTrue(settings, "shareStats")) {
            // Add public stats like total notes, events, etc.
            publicStats.put("totalNotes", getTotalNotes(user));
            publicStats.put("totalEvents", getTotalEvents(user));
            publicStats.put("totalDiaryEntries", getTotalDiaryEntries(user));
        }
        dto.setPublicStats(publicStats);
        
        // Build social connections (only if allowed)
        if (isTrue(settings, "shareSocialConnections")) {
            Map<String, Object> social = new HashMap<>();
            if (isTrue(settings, "shareGithub")) {
                social.put("github", getGithubData(user));
            }
            if (isTrue(settings, "shareLeetcode")) {
                social.put("leetcode", getLeetcodeData(user));
            }
            if (isTrue(settings, "shareLinkedin")) {
                social.put("linkedin", getLinkedinData(user));
            }
            if (isTrue(settings, "shareInstagram")) {
                social.put("instagram", getInstagramData(user));
            }
            if (isTrue(settings, "shareFacebook")) {
                social.put("facebook", getFacebookData(user));
            }
            if (isTrue(settings, "shareYoutube")) {
                social.put("youtube", getYoutubeData(user));
            }
            if (isTrue(settings, "shareSnapchat")) {
                social.put("snapchat", getSnapchatData(user));
            }
            dto.setSocialConnections(social);
        }
        
        if (isTrue(settings, "sharePlantStreak")) {
            PlantStreak streak = getPlantStreak(user);
            dto.setPlantStreak(streak != null ? streak.getCurrentStreak() : 0);
            dto.setPlantEmoji(getPlantEmoji(streak != null ? streak.getCurrentStreak() : 0));
        }
        
        return dto;
    }
    
    private boolean isTrue(Map<String, Object> settings, String key) {
        return settings.containsKey(key) && Boolean.TRUE.equals(settings.get(key));
    }
    
    // Placeholder methods - implement with your actual data access
    private String loadProfilePicture(String username) {
        // Implement based on your storage
        return null;
    }
    
    private int getTotalNotes(User user) {
        // Implement with your Notes repository
        return 0;
    }
    
    private int getTotalEvents(User user) {
        // Implement with your Events repository
        return 0;
    }
    
    private int getTotalDiaryEntries(User user) {
        // Implement with your Diary repository
        return 0;
    }
    
    private Object getGithubData(User user) {
        // Implement with your SocialStats repository
        return null;
    }
    
    private Object getLeetcodeData(User user) {
        return null;
    }
    
    private Object getLinkedinData(User user) {
        return null;
    }
    
    private Object getInstagramData(User user) {
        return null;
    }
    
    private Object getFacebookData(User user) {
        return null;
    }
    
    private Object getYoutubeData(User user) {
        return null;
    }
    
    private Object getSnapchatData(User user) {
        return null;
    }
    
    private PlantStreak getPlantStreak(User user) {
        // Implement with your PlantStreak repository
        return null;
    }
    
    private String getPlantEmoji(int streak) {
        if (streak >= 30) return "🌳";
        else if (streak >= 14) return "🌿";
        else if (streak >= 7) return "🌱";
        else if (streak >= 3) return "🌿";
        else return "🌱";
    }
}