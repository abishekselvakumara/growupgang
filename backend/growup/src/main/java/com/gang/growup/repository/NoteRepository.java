package com.gang.growup.repository;

import com.gang.growup.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUserUsername(String username);
    List<Note> findByUserUsernameAndImportantTrue(String username);
}