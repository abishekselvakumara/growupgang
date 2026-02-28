package com.gang.growup.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String email;
    private String fullName;
    
    // 🔥 NEVER expose password in JSON
    @JsonIgnore
    private String password;

    // User-specific stats
    private Integer codingStreak = 0;
    private Integer weeklyActive = 0;
    private String averageMood = "7.2/10";

    // 🔥 IMPORTANT → prevent infinite JSON recursion
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SocialStat> socialStats = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Diary> diaryEntries = new ArrayList<>();

    public User() {}

    public User(String username, String email, String fullName, String password) {
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
    }

    // ========= GETTERS & SETTERS =========

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Integer getCodingStreak() { return codingStreak; }
    public void setCodingStreak(Integer codingStreak) { this.codingStreak = codingStreak; }

    public Integer getWeeklyActive() { return weeklyActive; }
    public void setWeeklyActive(Integer weeklyActive) { this.weeklyActive = weeklyActive; }

    public String getAverageMood() { return averageMood; }
    public void setAverageMood(String averageMood) { this.averageMood = averageMood; }

    public List<Note> getNotes() { return notes; }
    public void setNotes(List<Note> notes) { this.notes = notes; }

    public List<Event> getEvents() { return events; }
    public void setEvents(List<Event> events) { this.events = events; }

    public List<Transaction> getTransactions() { return transactions; }
    public void setTransactions(List<Transaction> transactions) { this.transactions = transactions; }

    public List<SocialStat> getSocialStats() { return socialStats; }
    public void setSocialStats(List<SocialStat> socialStats) { this.socialStats = socialStats; }

    public List<Diary> getDiaryEntries() { return diaryEntries; }
    public void setDiaryEntries(List<Diary> diaryEntries) { this.diaryEntries = diaryEntries; }
}