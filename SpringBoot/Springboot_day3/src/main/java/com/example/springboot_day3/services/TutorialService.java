package com.example.springboot_day3.services;

import com.example.springboot_day3.entities.Tutorial;
import com.example.springboot_day3.entities.User;
import com.example.springboot_day3.repository.TutorialRepository;
import com.example.springboot_day3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TutorialService {
    private final TutorialRepository repo;
    private final UserRepository userRepo;

    public List<Tutorial> getAll(){
        return repo.findAll();
    }
    public Tutorial createTutorial(Long userId, Tutorial tutorial){
        User user = userRepo.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"));
        tutorial.setUser(user);
        return repo.save(tutorial);
    }
    public List<Tutorial> getByUser(Long userId){
        return repo.findByUserId(userId);
    }
}
