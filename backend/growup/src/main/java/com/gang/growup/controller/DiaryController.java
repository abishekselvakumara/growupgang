package com.gang.growup.controller;

import com.gang.growup.dto.DiaryDTO;
import com.gang.growup.entity.Diary;
import com.gang.growup.entity.User;
import com.gang.growup.repository.DiaryRepository;
import com.gang.growup.repository.UserRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/diary")
@CrossOrigin(origins = "http://localhost:5173")
public class DiaryController {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

    public DiaryController(DiaryRepository diaryRepository,
                           UserRepository userRepository) {
        this.diaryRepository = diaryRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        Object principal =
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return userRepository.findByUsername(username).orElseThrow();
    }

    private DiaryDTO toDTO(Diary d) {
        DiaryDTO dto = new DiaryDTO();
        dto.setId(d.getId());
        dto.setTitle(d.getTitle());
        dto.setContent(d.getContent());
        dto.setDate(d.getEntryDate());
        dto.setTime(d.getEntryTime());
        return dto;
    }

    @GetMapping
    public List<DiaryDTO> getAll() {
        User user = getCurrentUser();

        return diaryRepository
                .findByUserOrderByEntryDateDescEntryTimeDesc(user)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public DiaryDTO create(@RequestBody Diary diary) {

        User user = getCurrentUser();

        diary.setUser(user);
        diary.setCreatedAt(LocalDateTime.now());

        if (diary.getEntryDate() == null)
            diary.setEntryDate(LocalDate.now());

        if (diary.getEntryTime() == null)
            diary.setEntryTime(LocalTime.now());

        return toDTO(diaryRepository.save(diary));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        User user = getCurrentUser();

        Diary diary = diaryRepository.findById(id).orElseThrow();

        if (!diary.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        diaryRepository.delete(diary);
    }
}