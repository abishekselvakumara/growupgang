package com.gang.growup.repository;

import com.gang.growup.entity.SharedProfile;
import com.gang.growup.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SharedProfileRepository extends JpaRepository<SharedProfile, Long> {
    
    Optional<SharedProfile> findByUser(User user);
    
    Optional<SharedProfile> findByShareCode(String shareCode);
    
    Optional<SharedProfile> findByShareUrl(String shareUrl);
    
    List<SharedProfile> findByIsActiveTrue();
    
    @Query("SELECT sp FROM SharedProfile sp WHERE sp.isActive = true AND sp.expiresAt < :now")
    List<SharedProfile> findExpiredProfiles(@Param("now") LocalDateTime now);
    
    @Modifying
    @Transactional
    @Query("UPDATE SharedProfile sp SET sp.viewsCount = sp.viewsCount + 1, sp.lastViewedAt = :now WHERE sp.shareCode = :shareCode")
    int incrementViews(@Param("shareCode") String shareCode, @Param("now") LocalDateTime now);
    
    boolean existsByShareCode(String shareCode);
    
    boolean existsByShareUrl(String shareUrl);
}