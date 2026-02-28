package com.gang.growup.repository;

import com.gang.growup.entity.PlantStreak;
import com.gang.growup.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlantStreakRepository extends JpaRepository<PlantStreak, Long> {
    
    Optional<PlantStreak> findByUser(User user);
    
    Optional<PlantStreak> findByUserId(Long userId);
    
    @Query("SELECT ps FROM PlantStreak ps WHERE ps.lastWateredDate = :date")
    List<PlantStreak> findByLastWateredDate(@Param("date") LocalDate date);
    
    @Query("SELECT ps FROM PlantStreak ps WHERE ps.currentStreak > 0 ORDER BY ps.currentStreak DESC")
    List<PlantStreak> findAllActiveStreaks();
    
    boolean existsByUser(User user);
}