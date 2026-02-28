package com.gang.growup.repository;

import com.gang.growup.entity.SocialStat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SocialStatRepository extends JpaRepository<SocialStat, Long> {
    List<SocialStat> findByUserUsername(String username);
    Optional<SocialStat> findByUserUsernameAndPlatform(String username, String platform);
}