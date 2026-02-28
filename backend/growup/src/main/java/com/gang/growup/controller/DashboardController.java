package com.gang.growup.controller;

import com.gang.growup.dto.DashboardSummaryDTO;
import com.gang.growup.entity.Note;
import com.gang.growup.entity.Event;
import com.gang.growup.repository.NoteRepository;
import com.gang.growup.repository.EventRepository;
import com.gang.growup.repository.TransactionRepository;
import com.gang.growup.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:5173")
public class DashboardController {

    private final TransactionRepository transactionRepository;
    private final NoteRepository noteRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public DashboardController(
            TransactionRepository transactionRepository,
            NoteRepository noteRepository,
            EventRepository eventRepository,
            UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.noteRepository = noteRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryDTO> getSummary(Authentication authentication) {
        String username = authentication.getName();
        
        DashboardSummaryDTO summary = new DashboardSummaryDTO();
        
        // Finance summary
        Double totalIncome = transactionRepository.getTotalIncome(username);
        Double totalExpenses = transactionRepository.getTotalExpenses(username);
        summary.setTotalIncome(totalIncome != null ? totalIncome : 0.0);
        summary.setTotalExpenses(totalExpenses != null ? totalExpenses : 0.0);
        summary.setSavings(summary.getTotalIncome() - summary.getTotalExpenses());
        
        // Notes summary
        List<Note> allNotes = noteRepository.findByUserUsername(username);
        List<Note> importantNotes = noteRepository.findByUserUsernameAndImportantTrue(username);
        summary.setTotalNotes(allNotes.size());
        summary.setImportantNotes(importantNotes.size());
        
        // Events summary
        List<Event> allEvents = eventRepository.findByUserUsername(username);
        String today = LocalDate.now().toString();
        List<Event> upcomingEvents = eventRepository.findByUserUsernameAndDateAfterOrderByDateAsc(username, today);
        summary.setTotalEvents(allEvents.size());
        summary.setUpcomingEvents(upcomingEvents.size());
        
        // Coding stats (you can store these in User entity or separate table)
        summary.setCodingStreak(7); // Placeholder - implement as needed
        summary.setWeeklyActive(42); // Placeholder - implement as needed
        
        return ResponseEntity.ok(summary);
    }
}