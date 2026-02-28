package com.gang.growup.controller;

import com.gang.growup.dto.NoteDTO;
import com.gang.growup.entity.Note;
import com.gang.growup.entity.User;
import com.gang.growup.repository.NoteRepository;
import com.gang.growup.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "http://localhost:5173")
public class NoteController {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteController(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Note> getNotes(Authentication authentication) {
        String username = authentication.getName();
        return noteRepository.findByUserUsername(username);
    }

    @PostMapping
    public Note createNote(@RequestBody NoteDTO noteDTO, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Note note = new Note(
                noteDTO.getTitle(),
                noteDTO.getContent(),
                noteDTO.isImportant(),
                noteDTO.getDate(),
                user
        );

        return noteRepository.save(note);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody NoteDTO noteDTO, Authentication authentication) {
        String username = authentication.getName();
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        if (!note.getUser().getUsername().equals(username)) {
            return ResponseEntity.status(403).build();
        }

        note.setTitle(noteDTO.getTitle());
        note.setContent(noteDTO.getContent());
        note.setImportant(noteDTO.isImportant());
        note.setDate(noteDTO.getDate());

        return ResponseEntity.ok(noteRepository.save(note));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        if (!note.getUser().getUsername().equals(username)) {
            return ResponseEntity.status(403).build();
        }

        noteRepository.delete(note);
        return ResponseEntity.ok().build();
    }
}