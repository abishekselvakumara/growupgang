package com.gang.growup.controller;

import com.gang.growup.dto.SocialStatDTO;
import com.gang.growup.entity.SocialStat;
import com.gang.growup.entity.User;
import com.gang.growup.repository.SocialStatRepository;
import com.gang.growup.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/social")
@CrossOrigin(origins = "http://localhost:5173")
public class SocialController {

    private final SocialStatRepository socialStatRepository;
    private final UserRepository userRepository;

    public SocialController(SocialStatRepository socialStatRepository, UserRepository userRepository) {
        this.socialStatRepository = socialStatRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<SocialStat> getSocialStats(Authentication authentication) {
        String username = authentication.getName();
        return socialStatRepository.findByUserUsername(username);
    }

    @GetMapping("/grouped")
    public ResponseEntity<?> getGroupedStats(Authentication authentication) {
        String username = authentication.getName();
        List<SocialStat> stats = socialStatRepository.findByUserUsername(username);
        
        Map<String, SocialStat> grouped = stats.stream()
                .collect(Collectors.toMap(
                    SocialStat::getPlatform,
                    stat -> stat,
                    (existing, replacement) -> existing
                ));
        
        return ResponseEntity.ok(grouped);
    }

    @PostMapping("/{platform}")
    public ResponseEntity<SocialStat> saveManualStat(
            @PathVariable String platform,
            @RequestBody SocialStatDTO statDTO,
            Authentication authentication) {
        
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        SocialStat stat = socialStatRepository
                .findByUserUsernameAndPlatform(username, platform)
                .orElse(new SocialStat());

        stat.setPlatform(platform);
        stat.setSource("manual");
        stat.setValue(statDTO.getValue());
        stat.setLabel(statDTO.getLabel());
        stat.setNote(statDTO.getNote());
        stat.setUser(user);

        return ResponseEntity.ok(socialStatRepository.save(stat));
    }

    @PostMapping("/{platform}/api")
    public ResponseEntity<SocialStat> saveApiStat(
            @PathVariable String platform,
            @RequestBody SocialStatDTO statDTO,
            Authentication authentication) {
        
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        SocialStat stat = socialStatRepository
                .findByUserUsernameAndPlatform(username, platform)
                .orElse(new SocialStat());

        stat.setPlatform(platform);
        stat.setSource("api");
        stat.setValue(statDTO.getValue());
        stat.setLabel(statDTO.getLabel());
        stat.setUser(user);

        return ResponseEntity.ok(socialStatRepository.save(stat));
    }
}