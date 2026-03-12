package com.example.Springboot_day4.repositories;

import com.example.Springboot_day4.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
