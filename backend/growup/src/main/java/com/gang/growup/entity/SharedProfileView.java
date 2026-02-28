package com.gang.growup.entity;

import jakarta.persistence.*; 
import java.time.LocalDateTime;

@Entity
@Table(name = "shared_profile_views")
public class SharedProfileView {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "shared_profile_id", nullable = false)
    private SharedProfile sharedProfile;
    
    @Column(name = "viewer_ip", length = 45)
    private String viewerIp;
    
    @Column(name = "viewer_user_agent", columnDefinition = "TEXT")
    private String viewerUserAgent;
    
    @Column(name = "viewed_at")
    private LocalDateTime viewedAt;
    
    public SharedProfileView() {}
    
    public SharedProfileView(SharedProfile sharedProfile, String viewerIp, String viewerUserAgent) {
        this.sharedProfile = sharedProfile;
        this.viewerIp = viewerIp;
        this.viewerUserAgent = viewerUserAgent;
        this.viewedAt = LocalDateTime.now();
    }
    
    @PrePersist
    protected void onCreate() {
        viewedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public SharedProfile getSharedProfile() {
        return sharedProfile;
    }
    
    public void setSharedProfile(SharedProfile sharedProfile) {
        this.sharedProfile = sharedProfile;
    }
    
    public String getViewerIp() {
        return viewerIp;
    }
    
    public void setViewerIp(String viewerIp) {
        this.viewerIp = viewerIp;
    }
    
    public String getViewerUserAgent() {
        return viewerUserAgent;
    }
    
    public void setViewerUserAgent(String viewerUserAgent) {
        this.viewerUserAgent = viewerUserAgent;
    }
    
    public LocalDateTime getViewedAt() {
        return viewedAt;
    }
    
    public void setViewedAt(LocalDateTime viewedAt) {
        this.viewedAt = viewedAt;
    }
}