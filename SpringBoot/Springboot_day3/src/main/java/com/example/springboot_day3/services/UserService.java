package com.example.springboot_day3.services;

import com.example.springboot_day3.entities.User;
import com.example.springboot_day3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repo;
    public List<User> getAll(){
        return repo.findAll();
    }
    public User getById(Long id){
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    public User createUser(User user){
        return repo.save(user);
    }
}
