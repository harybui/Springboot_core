package com.example.Springboot_day4.repositories;

import com.example.Springboot_day4.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Long> {
}
