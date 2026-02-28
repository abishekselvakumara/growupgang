package com.gang.growup.controller;

import com.gang.growup.dto.PlantStreakDTO;
import com.gang.growup.entity.User;
import com.gang.growup.service.PlantStreakService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user/plant-streak")
@CrossOrigin(origins = "http://localhost:5173")
public class PlantStreakController {
    
    private final PlantStreakService plantStreakService;
    
    public PlantStreakController(PlantStreakService plantStreakService) {
        this.plantStreakService = plantStreakService;
    }
    
    @GetMapping
    public ResponseEntity<?> getPlantStreak(@AuthenticationPrincipal User user) {
        try {
            PlantStreakDTO streak = plantStreakService.getPlantStreak(user);
            return ResponseEntity.ok(streak);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch plant streak");
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping("/water")
    public ResponseEntity<?> waterPlant(@AuthenticationPrincipal User user) {
        try {
            PlantStreakDTO updatedStreak = plantStreakService.waterPlant(user);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Plant watered successfully! 🌱");
            response.put("streak", updatedStreak);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to water plant");
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping("/reset")
    public ResponseEntity<?> resetStreak(@AuthenticationPrincipal User user) {
        try {
            PlantStreakDTO resetStreak = plantStreakService.resetStreak(user);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Streak reset");
            response.put("streak", resetStreak);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to reset streak");
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping("/update")
    public ResponseEntity<?> updateStreak(
            @AuthenticationPrincipal User user,
            @RequestBody Map<String, Boolean> request) {
        try {
            boolean watered = request.getOrDefault("watered", false);
            PlantStreakDTO updatedStreak = plantStreakService.updateStreak(user, watered);
            return ResponseEntity.ok(updatedStreak);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to update streak");
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @GetMapping("/status")
    public ResponseEntity<?> getStreakStatus(@AuthenticationPrincipal User user) {
        try {
            PlantStreakDTO streak = plantStreakService.getPlantStreak(user);
            Map<String, Object> status = new HashMap<>();
            status.put("currentStreak", streak.getCurrentStreak());
            status.put("longestStreak", streak.getLongestStreak());
            status.put("wateredToday", streak.isWateredToday());
            status.put("message", streak.getStreakMessage());
            status.put("emoji", streak.getPlantEmoji());
            status.put("totalWaterings", streak.getTotalWaterings());
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to get streak status");
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}