package com.gang.growup.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class SharedProfileDTO {
    private Long id;
    private String shareCode;
    private String shareUrl;
    private Boolean isActive;
    private LocalDateTime expiresAt;
    private Integer viewsCount;
    private LocalDateTime lastViewedAt;
    private Map<String, Object> shareSettings;
    private LocalDateTime createdAt;
    
    // Public profile data (only what's allowed to share)
    private String username;
    private String fullName;
    private String profilePicture;
    private String bio;
    private String location;
    private String website;
    private String joinDate;
    private Map<String, Object> publicStats;
    private Map<String, Object> socialConnections;
    private Integer plantStreak;
    private String plantEmoji;
    
    public SharedProfileDTO() {}
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getShareCode() { return shareCode; }
    public void setShareCode(String shareCode) { this.shareCode = shareCode; }
    
    public String getShareUrl() { return shareUrl; }
    public void setShareUrl(String shareUrl) { this.shareUrl = shareUrl; }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
    
    public Integer getViewsCount() { return viewsCount; }
    public void setViewsCount(Integer viewsCount) { this.viewsCount = viewsCount; }
    
    public LocalDateTime getLastViewedAt() { return lastViewedAt; }
    public void setLastViewedAt(LocalDateTime lastViewedAt) { this.lastViewedAt = lastViewedAt; }
    
    public Map<String, Object> getShareSettings() { return shareSettings; }
    public void setShareSettings(Map<String, Object> shareSettings) { this.shareSettings = shareSettings; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public String getProfilePicture() { return profilePicture; }
    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }
    
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
    
    public String getJoinDate() { return joinDate; }
    public void setJoinDate(String joinDate) { this.joinDate = joinDate; }
    
    public Map<String, Object> getPublicStats() { return publicStats; }
    public void setPublicStats(Map<String, Object> publicStats) { this.publicStats = publicStats; }
    
    public Map<String, Object> getSocialConnections() { return socialConnections; }
    public void setSocialConnections(Map<String, Object> socialConnections) { this.socialConnections = socialConnections; }
    
    public Integer getPlantStreak() { return plantStreak; }
    public void setPlantStreak(Integer plantStreak) { this.plantStreak = plantStreak; }
    
    public String getPlantEmoji() { return plantEmoji; }
    public void setPlantEmoji(String plantEmoji) { this.plantEmoji = plantEmoji; }
}