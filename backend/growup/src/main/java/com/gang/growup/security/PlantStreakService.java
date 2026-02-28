package com.gang.growup.service;

import com.gang.growup.entity.User;
import com.gang.growup.dto.PlantStreakDTO;
import java.util.List;

public interface PlantStreakService {
    
    PlantStreakDTO getPlantStreak(User user);
    
    PlantStreakDTO waterPlant(User user);
    
    PlantStreakDTO resetStreak(User user);
    
    PlantStreakDTO updateStreak(User user, boolean watered);
    
    List<PlantStreakDTO> getLeaderboard();
    
    long getActiveUsersCount();
    
    void checkAndResetStaleStreaks();
}