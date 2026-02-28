package com.gang.growup.entity;

import jakarta.persistence.*; 
import java.time.LocalDateTime;

@Entity
@Table(name = "shared_profiles")
public class SharedProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "share_code", unique = true, nullable = false, length = 50)
    private String shareCode;
    
    @Column(name = "share_url", unique = true, nullable = false, length = 255)
    private String shareUrl;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
    
    @Column(name = "views_count")
    private Integer viewsCount = 0;
    
    @Column(name = "last_viewed_at")
    private LocalDateTime lastViewedAt;
    
    @Column(name = "share_settings", columnDefinition = "TEXT")
    private String shareSettings; // JSON string of what to share
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Constructors
    public SharedProfile() {}
    
    public SharedProfile(User user, String shareCode, String shareUrl, String shareSettings) {
        this.user = user;
        this.shareCode = shareCode;
        this.shareUrl = shareUrl;
        this.shareSettings = shareSettings;
        this.isActive = true;
        this.viewsCount = 0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
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
    
    public String getShareCode() {
        return shareCode;
    }
    
    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }
    
    public String getShareUrl() {
        return shareUrl;
    }
    
    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
    
    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
    
    public Integer getViewsCount() {
        return viewsCount;
    }
    
    public void setViewsCount(Integer viewsCount) {
        this.viewsCount = viewsCount;
    }
    
    public LocalDateTime getLastViewedAt() {
        return lastViewedAt;
    }
    
    public void setLastViewedAt(LocalDateTime lastViewedAt) {
        this.lastViewedAt = lastViewedAt;
    }
    
    public String getShareSettings() {
        return shareSettings;
    }
    
    public void setShareSettings(String shareSettings) {
        this.shareSettings = shareSettings;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}