package com.gang.growup.repository;

import com.gang.growup.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByUserUsername(String username);
    List<Event> findByUserUsernameAndDate(String username, String date);
    List<Event> findByUserUsernameAndDateAfterOrderByDateAsc(String username, String date);
}