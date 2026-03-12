package com.example.Springboot_day4.repositories;

import com.example.Springboot_day4.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
