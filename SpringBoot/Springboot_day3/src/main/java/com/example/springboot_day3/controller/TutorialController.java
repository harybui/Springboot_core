package com.example.springboot_day3.controller;

import com.example.springboot_day3.entities.Tutorial;
import com.example.springboot_day3.services.TutorialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tutorial")
@RequiredArgsConstructor
public class TutorialController {
    private final TutorialService service;

    @GetMapping
    public ResponseEntity<List<Tutorial>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<Tutorial> create(@PathVariable Long userId, @RequestBody Tutorial tutorial) {
        return ResponseEntity.ok(service.createTutorial(userId, tutorial));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Tutorial>> getTutorialByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getByUser(userId));
    }
}
