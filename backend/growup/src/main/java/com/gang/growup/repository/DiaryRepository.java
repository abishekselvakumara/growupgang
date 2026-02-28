package com.gang.growup.repository;

import com.gang.growup.entity.Diary;
import com.gang.growup.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    List<Diary> findByUserOrderByEntryDateDescEntryTimeDesc(User user);
}