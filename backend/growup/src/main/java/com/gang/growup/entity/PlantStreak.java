package com.gang.growup.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;   // 🔥 CHANGE THIS
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "plant_streaks")
public class PlantStreak {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonIgnore
    private User user;
    
    @Column(name = "current_streak", nullable = false)
    private int currentStreak = 0;
    
    @Column(name = "longest_streak", nullable = false)
    private int longestStreak = 0;
    
    @Column(name = "last_watered_date")
    private LocalDate lastWateredDate;
    
    @Column(name = "streak_start_date")
    private LocalDate streakStartDate;
    
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    @Column(name = "total_waterings")
    private int totalWaterings = 0;
    
    // Constructors
    public PlantStreak() {}
    
    public PlantStreak(User user) {
        this.user = user;
        this.currentStreak = 0;
        this.longestStreak = 0;
        this.totalWaterings = 0;
        this.lastUpdated = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
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
    
    @PreUpdate
    protected void onUpdate() {
        lastUpdated = LocalDateTime.now();
    }
}