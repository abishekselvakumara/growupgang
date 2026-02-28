package com.gang.growup.controller;

import com.gang.growup.dto.EventDTO;
import com.gang.growup.entity.Event;
import com.gang.growup.entity.User;
import com.gang.growup.repository.EventRepository;
import com.gang.growup.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:5173")
public class EventController {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public EventController(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Event> getEvents(Authentication authentication) {
        String username = authentication.getName();
        return eventRepository.findByUserUsername(username);
    }

    @GetMapping("/today")
    public List<Event> getTodayEvents(Authentication authentication) {
        String username = authentication.getName();
        String today = LocalDate.now().toString();
        return eventRepository.findByUserUsernameAndDate(username, today);
    }

    @GetMapping("/upcoming")
    public List<Event> getUpcomingEvents(Authentication authentication) {
        String username = authentication.getName();
        String today = LocalDate.now().toString();
        return eventRepository.findByUserUsernameAndDateAfterOrderByDateAsc(username, today);
    }

    @PostMapping
    public Event createEvent(@RequestBody EventDTO eventDTO, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Event event = new Event(
                eventDTO.getTitle(),
                eventDTO.getDescription(),
                eventDTO.getDate(),
                eventDTO.getTime(),
                eventDTO.getType(),
                user
        );

        return eventRepository.save(event);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody EventDTO eventDTO, Authentication authentication) {
        String username = authentication.getName();
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (!event.getUser().getUsername().equals(username)) {
            return ResponseEntity.status(403).build();
        }

        event.setTitle(eventDTO.getTitle());
        event.setDescription(eventDTO.getDescription());
        event.setDate(eventDTO.getDate());
        event.setTime(eventDTO.getTime());
        event.setType(eventDTO.getType());

        return ResponseEntity.ok(eventRepository.save(event));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (!event.getUser().getUsername().equals(username)) {
            return ResponseEntity.status(403).build();
        }

        eventRepository.delete(event);
        return ResponseEntity.ok().build();
    }
}