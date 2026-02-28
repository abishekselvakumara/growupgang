package com.gang.growup.dto;

import java.util.HashMap;
import java.util.Map;

public class ShareSettingsDTO {
    private boolean shareName = true;
    private boolean shareUsername = true;
    private boolean shareBio = false;
    private boolean shareLocation = false;
    private boolean shareWebsite = false;
    private boolean shareJoinDate = true;
    private boolean shareProfilePicture = true;
    private boolean shareStats = true;
    private boolean shareSocialConnections = false;
    private boolean shareAchievements = true;
    private boolean sharePlantStreak = true;
    
    // Social platform sharing (individual control)
    private boolean shareGithub = false;
    private boolean shareLeetcode = false;
    private boolean shareLinkedin = false;
    private boolean shareInstagram = false;
    private boolean shareFacebook = false;
    private boolean shareYoutube = false;
    private boolean shareSnapchat = false;
    
    // Expiry settings
    private String expiryOption = "never"; // never, 24h, 7d, 30d, custom
    private String customExpiryDate;
    
    public ShareSettingsDTO() {}
    
    // Convert to Map for JSON storage
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("shareName", shareName);
        map.put("shareUsername", shareUsername);
        map.put("shareBio", shareBio);
        map.put("shareLocation", shareLocation);
        map.put("shareWebsite", shareWebsite);
        map.put("shareJoinDate", shareJoinDate);
        map.put("shareProfilePicture", shareProfilePicture);
        map.put("shareStats", shareStats);
        map.put("shareSocialConnections", shareSocialConnections);
        map.put("shareAchievements", shareAchievements);
        map.put("sharePlantStreak", sharePlantStreak);
        map.put("shareGithub", shareGithub);
        map.put("shareLeetcode", shareLeetcode);
        map.put("shareLinkedin", shareLinkedin);
        map.put("shareInstagram", shareInstagram);
        map.put("shareFacebook", shareFacebook);
        map.put("shareYoutube", shareYoutube);
        map.put("shareSnapchat", shareSnapchat);
        map.put("expiryOption", expiryOption);
        map.put("customExpiryDate", customExpiryDate);
        return map;
    }
    
    // Create from Map
    public static ShareSettingsDTO fromMap(Map<String, Object> map) {
        ShareSettingsDTO dto = new ShareSettingsDTO();
        if (map.containsKey("shareName")) dto.setShareName((Boolean) map.get("shareName"));
        if (map.containsKey("shareUsername")) dto.setShareUsername((Boolean) map.get("shareUsername"));
        if (map.containsKey("shareBio")) dto.setShareBio((Boolean) map.get("shareBio"));
        if (map.containsKey("shareLocation")) dto.setShareLocation((Boolean) map.get("shareLocation"));
        if (map.containsKey("shareWebsite")) dto.setShareWebsite((Boolean) map.get("shareWebsite"));
        if (map.containsKey("shareJoinDate")) dto.setShareJoinDate((Boolean) map.get("shareJoinDate"));
        if (map.containsKey("shareProfilePicture")) dto.setShareProfilePicture((Boolean) map.get("shareProfilePicture"));
        if (map.containsKey("shareStats")) dto.setShareStats((Boolean) map.get("shareStats"));
        if (map.containsKey("shareSocialConnections")) dto.setShareSocialConnections((Boolean) map.get("shareSocialConnections"));
        if (map.containsKey("shareAchievements")) dto.setShareAchievements((Boolean) map.get("shareAchievements"));
        if (map.containsKey("sharePlantStreak")) dto.setSharePlantStreak((Boolean) map.get("sharePlantStreak"));
        if (map.containsKey("shareGithub")) dto.setShareGithub((Boolean) map.get("shareGithub"));
        if (map.containsKey("shareLeetcode")) dto.setShareLeetcode((Boolean) map.get("shareLeetcode"));
        if (map.containsKey("shareLinkedin")) dto.setShareLinkedin((Boolean) map.get("shareLinkedin"));
        if (map.containsKey("shareInstagram")) dto.setShareInstagram((Boolean) map.get("shareInstagram"));
        if (map.containsKey("shareFacebook")) dto.setShareFacebook((Boolean) map.get("shareFacebook"));
        if (map.containsKey("shareYoutube")) dto.setShareYoutube((Boolean) map.get("shareYoutube"));
        if (map.containsKey("shareSnapchat")) dto.setShareSnapchat((Boolean) map.get("shareSnapchat"));
        if (map.containsKey("expiryOption")) dto.setExpiryOption((String) map.get("expiryOption"));
        if (map.containsKey("customExpiryDate")) dto.setCustomExpiryDate((String) map.get("customExpiryDate"));
        return dto;
    }
    
    // Getters and Setters
    public boolean isShareName() { return shareName; }
    public void setShareName(boolean shareName) { this.shareName = shareName; }
    
    public boolean isShareUsername() { return shareUsername; }
    public void setShareUsername(boolean shareUsername) { this.shareUsername = shareUsername; }
    
    public boolean isShareBio() { return shareBio; }
    public void setShareBio(boolean shareBio) { this.shareBio = shareBio; }
    
    public boolean isShareLocation() { return shareLocation; }
    public void setShareLocation(boolean shareLocation) { this.shareLocation = shareLocation; }
    
    public boolean isShareWebsite() { return shareWebsite; }
    public void setShareWebsite(boolean shareWebsite) { this.shareWebsite = shareWebsite; }
    
    public boolean isShareJoinDate() { return shareJoinDate; }
    public void setShareJoinDate(boolean shareJoinDate) { this.shareJoinDate = shareJoinDate; }
    
    public boolean isShareProfilePicture() { return shareProfilePicture; }
    public void setShareProfilePicture(boolean shareProfilePicture) { this.shareProfilePicture = shareProfilePicture; }
    
    public boolean isShareStats() { return shareStats; }
    public void setShareStats(boolean shareStats) { this.shareStats = shareStats; }
    
    public boolean isShareSocialConnections() { return shareSocialConnections; }
    public void setShareSocialConnections(boolean shareSocialConnections) { this.shareSocialConnections = shareSocialConnections; }
    
    public boolean isShareAchievements() { return shareAchievements; }
    public void setShareAchievements(boolean shareAchievements) { this.shareAchievements = shareAchievements; }
    
    public boolean isSharePlantStreak() { return sharePlantStreak; }
    public void setSharePlantStreak(boolean sharePlantStreak) { this.sharePlantStreak = sharePlantStreak; }
    
    public boolean isShareGithub() { return shareGithub; }
    public void setShareGithub(boolean shareGithub) { this.shareGithub = shareGithub; }
    
    public boolean isShareLeetcode() { return shareLeetcode; }
    public void setShareLeetcode(boolean shareLeetcode) { this.shareLeetcode = shareLeetcode; }
    
    public boolean isShareLinkedin() { return shareLinkedin; }
    public void setShareLinkedin(boolean shareLinkedin) { this.shareLinkedin = shareLinkedin; }
    
    public boolean isShareInstagram() { return shareInstagram; }
    public void setShareInstagram(boolean shareInstagram) { this.shareInstagram = shareInstagram; }
    
    public boolean isShareFacebook() { return shareFacebook; }
    public void setShareFacebook(boolean shareFacebook) { this.shareFacebook = shareFacebook; }
    
    public boolean isShareYoutube() { return shareYoutube; }
    public void setShareYoutube(boolean shareYoutube) { this.shareYoutube = shareYoutube; }
    
    public boolean isShareSnapchat() { return shareSnapchat; }
    public void setShareSnapchat(boolean shareSnapchat) { this.shareSnapchat = shareSnapchat; }
    
    public String getExpiryOption() { return expiryOption; }
    public void setExpiryOption(String expiryOption) { this.expiryOption = expiryOption; }
    
    public String getCustomExpiryDate() { return customExpiryDate; }
    public void setCustomExpiryDate(String customExpiryDate) { this.customExpiryDate = customExpiryDate; }
}