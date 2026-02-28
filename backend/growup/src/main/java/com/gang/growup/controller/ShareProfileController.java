package com.gang.growup.controller;

import com.gang.growup.dto.ShareSettingsDTO;
import com.gang.growup.dto.SharedProfileDTO;
import com.gang.growup.entity.User;
import com.gang.growup.service.ShareProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/share")
@CrossOrigin(origins = "http://localhost:5173")
public class ShareProfileController {
    
    private final ShareProfileService shareProfileService;
    
    public ShareProfileController(ShareProfileService shareProfileService) {
        this.shareProfileService = shareProfileService;
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> createSharedProfile(
            @AuthenticationPrincipal User user,
            @RequestBody ShareSettingsDTO settings) {
        try {
            SharedProfileDTO profile = shareProfileService.createSharedProfile(user, settings);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Share link created successfully");
            response.put("profile", profile);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to create share link");
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @GetMapping("/{shareCode}")
    public ResponseEntity<?> getSharedProfile(
            @PathVariable String shareCode,
            HttpServletRequest request) {
        try {
            SharedProfileDTO profile = shareProfileService.getSharedProfile(shareCode);
            
            // Record view (async or in background)
            String ipAddress = request.getRemoteAddr();
            String userAgent = request.getHeader("User-Agent");
            shareProfileService.recordProfileView(shareCode, ipAddress, userAgent);
            
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Profile not found or expired");
            error.put("message", e.getMessage());
            return ResponseEntity.status(404).body(error);
        }
    }
    
    @PutMapping("/settings")
    public ResponseEntity<?> updateShareSettings(
            @AuthenticationPrincipal User user,
            @RequestBody ShareSettingsDTO settings) {
        try {
            SharedProfileDTO updated = shareProfileService.updateShareSettings(user, settings);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Share settings updated");
            response.put("profile", updated);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to update settings");
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @DeleteMapping("/deactivate")
    public ResponseEntity<?> deactivateSharedProfile(@AuthenticationPrincipal User user) {
        try {
            shareProfileService.deactivateSharedProfile(user);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Share link deactivated");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to deactivate");
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @GetMapping("/analytics")
    public ResponseEntity<?> getShareAnalytics(@AuthenticationPrincipal User user) {
        try {
            Map<String, Object> analytics = shareProfileService.getShareAnalytics(user);
            return ResponseEntity.ok(analytics);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to get analytics");
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}