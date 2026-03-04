package com.example.springboot_day3.repository;

import com.example.springboot_day3.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
