package com.gang.growup.service.impl;

import com.gang.growup.entity.PlantStreak;
import com.gang.growup.entity.User;
import com.gang.growup.repository.PlantStreakRepository;
import com.gang.growup.service.PlantStreakService;
import com.gang.growup.dto.PlantStreakDTO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlantStreakServiceImpl implements PlantStreakService {
    
    private final PlantStreakRepository plantStreakRepository;
    
    public PlantStreakServiceImpl(PlantStreakRepository plantStreakRepository) {
        this.plantStreakRepository = plantStreakRepository;
    }
    
    @Override
    public PlantStreakDTO getPlantStreak(User user) {
        PlantStreak plantStreak = plantStreakRepository.findByUser(user)
            .orElseGet(() -> createNewPlantStreak(user));
        return convertToDTO(plantStreak);
    }
    
    @Override
    public PlantStreakDTO waterPlant(User user) {
        PlantStreak plantStreak = plantStreakRepository.findByUser(user)
            .orElseGet(() -> createNewPlantStreak(user));
        
        LocalDate today = LocalDate.now();
        
        // Check if already watered today
        if (today.equals(plantStreak.getLastWateredDate())) {
            return convertToDTO(plantStreak);
        }
        
        // Check if streak should continue or reset
        if (plantStreak.getLastWateredDate() != null) {
            LocalDate yesterday = today.minusDays(1);
            if (yesterday.equals(plantStreak.getLastWateredDate())) {
                // Consecutive day - increment streak
                plantStreak.setCurrentStreak(plantStreak.getCurrentStreak() + 1);
            } else {
                // Missed a day - reset streak to 1
                plantStreak.setCurrentStreak(1);
                plantStreak.setStreakStartDate(today);
            }
        } else {
            // First time watering
            plantStreak.setCurrentStreak(1);
            plantStreak.setStreakStartDate(today);
        }
        
        // Update longest streak if needed
        if (plantStreak.getCurrentStreak() > plantStreak.getLongestStreak()) {
            plantStreak.setLongestStreak(plantStreak.getCurrentStreak());
        }
        
        plantStreak.setLastWateredDate(today);
        plantStreak.setTotalWaterings(plantStreak.getTotalWaterings() + 1);
        plantStreak.setLastUpdated(LocalDateTime.now());
        
        PlantStreak saved = plantStreakRepository.save(plantStreak);
        System.out.println("User " + user.getUsername() + " watered plant. Streak: " + saved.getCurrentStreak());
        
        return convertToDTO(saved);
    }
    
    @Override
    public PlantStreakDTO resetStreak(User user) {
        PlantStreak plantStreak = plantStreakRepository.findByUser(user)
            .orElseGet(() -> createNewPlantStreak(user));
        
        plantStreak.setCurrentStreak(0);
        plantStreak.setLastWateredDate(null);
        plantStreak.setStreakStartDate(null);
        plantStreak.setLastUpdated(LocalDateTime.now());
        
        PlantStreak saved = plantStreakRepository.save(plantStreak);
        System.out.println("User " + user.getUsername() + " reset plant streak");
        
        return convertToDTO(saved);
    }
    
    @Override
    public PlantStreakDTO updateStreak(User user, boolean watered) {
        if (watered) {
            return waterPlant(user);
        } else {
            return resetStreak(user);
        }
    }
    
    @Override
    public List<PlantStreakDTO> getLeaderboard() {
        return plantStreakRepository.findAllActiveStreaks()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    public long getActiveUsersCount() {
        return plantStreakRepository.count();
    }
    
    @Override
    @Scheduled(cron = "0 0 0 * * *") // Run at midnight every day
    public void checkAndResetStaleStreaks() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        List<PlantStreak> allStreaks = plantStreakRepository.findAll();
        
        for (PlantStreak streak : allStreaks) {
            if (streak.getLastWateredDate() != null && 
                streak.getLastWateredDate().isBefore(yesterday)) {
                System.out.println("Resetting stale streak for user: " + streak.getUser().getUsername());
                streak.setCurrentStreak(0);
                streak.setStreakStartDate(null);
                streak.setLastUpdated(LocalDateTime.now());
                plantStreakRepository.save(streak);
            }
        }
    }
    
    private PlantStreak createNewPlantStreak(User user) {
        PlantStreak newStreak = new PlantStreak(user);
        return plantStreakRepository.save(newStreak);
    }
    
    private PlantStreakDTO convertToDTO(PlantStreak plantStreak) {
        PlantStreakDTO dto = new PlantStreakDTO();
        dto.setId(plantStreak.getId());
        dto.setUsername(plantStreak.getUser().getUsername());
        dto.setCurrentStreak(plantStreak.getCurrentStreak());
        dto.setLongestStreak(plantStreak.getLongestStreak());
        dto.setLastWateredDate(plantStreak.getLastWateredDate());
        dto.setStreakStartDate(plantStreak.getStreakStartDate());
        dto.setLastUpdated(plantStreak.getLastUpdated());
        dto.setTotalWaterings(plantStreak.getTotalWaterings());
        return dto;
    }
}