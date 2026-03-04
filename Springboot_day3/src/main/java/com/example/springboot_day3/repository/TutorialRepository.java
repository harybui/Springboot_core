package com.example.springboot_day3.repository;

import com.example.springboot_day3.entities.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TutorialRepository extends JpaRepository<Tutorial,Long> {
    List<Tutorial> findByPublished(boolean published);
    List<Tutorial> findByUserId(Long userId);
}
