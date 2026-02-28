package com.gang.growup.repository;

import com.gang.growup.entity.SharedProfile;
import com.gang.growup.entity.SharedProfileView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SharedProfileViewRepository extends JpaRepository<SharedProfileView, Long> {
    
    List<SharedProfileView> findBySharedProfile(SharedProfile sharedProfile);
    
    @Query("SELECT COUNT(spv) FROM SharedProfileView spv WHERE spv.sharedProfile = :profile")
    long countBySharedProfile(@Param("profile") SharedProfile profile);
    
    @Query("SELECT spv FROM SharedProfileView spv WHERE spv.sharedProfile = :profile ORDER BY spv.viewedAt DESC")
    List<SharedProfileView> findRecentViewsBySharedProfile(@Param("profile") SharedProfile profile);
    
    @Query("SELECT COUNT(spv) FROM SharedProfileView spv WHERE spv.sharedProfile = :profile AND spv.viewedAt > :since")
    long countViewsSince(@Param("profile") SharedProfile profile, @Param("since") LocalDateTime since);
}