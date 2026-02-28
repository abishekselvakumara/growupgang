package com.gang.growup.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PlantStreakDTO {
    private Long id;
    private String username;
    private int currentStreak;
    private int longestStreak;
    private LocalDate lastWateredDate;
    private LocalDate streakStartDate;
    private LocalDateTime lastUpdated;
    private int totalWaterings;
    
    // Constructors
    public PlantStreakDTO() {}
    
    public PlantStreakDTO(Long id, String username, int currentStreak, int longestStreak, 
                         LocalDate lastWateredDate, LocalDate streakStartDate, 
                         LocalDateTime lastUpdated, int totalWaterings) {
        this.id = id;
        this.username = username;
        this.currentStreak = currentStreak;
        this.longestStreak = longestStreak;
        this.lastWateredDate = lastWateredDate;
        this.streakStartDate = streakStartDate;
        this.lastUpdated = lastUpdated;
        this.totalWaterings = totalWaterings;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public int getCurrentStreak() {
        return currentStreak;
    }
    
    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }
    
    public int getLongestStreak() {
        return longestStreak;
    }
    
    public void setLongestStreak(int longestStreak) {
        this.longestStreak = longestStreak;
    }
    
    public LocalDate getLastWateredDate() {
        return lastWateredDate;
    }
    
    public void setLastWateredDate(LocalDate lastWateredDate) {
        this.lastWateredDate = lastWateredDate;
    }
    
    public LocalDate getStreakStartDate() {
        return streakStartDate;
    }
    
    public void setStreakStartDate(LocalDate streakStartDate) {
        this.streakStartDate = streakStartDate;
    }
    
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }
    
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
    public int getTotalWaterings() {
        return totalWaterings;
    }
    
    public void setTotalWaterings(int totalWaterings) {
        this.totalWaterings = totalWaterings;
    }
    
    // Helper methods
    public String getPlantEmoji() {
        if (currentStreak >= 30) return "🌳";
        else if (currentStreak >= 14) return "🌿";
        else if (currentStreak >= 7) return "🌱";
        else if (currentStreak >= 3) return "🌿";
        else return "🌱";
    }
    
    public boolean isWateredToday() {
        return lastWateredDate != null && lastWateredDate.equals(LocalDate.now());
    }
    
    public String getStreakMessage() {
        if (currentStreak == 0) return "Start your journey today! 🌱";
        else if (currentStreak == 1) return "First day! Keep it up! 🌿";
        else return String.format("Day %d! You're on fire! 🔥", currentStreak);
    }
}