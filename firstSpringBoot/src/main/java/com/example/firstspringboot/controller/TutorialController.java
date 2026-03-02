package com.example.firstspringboot.controller;

import com.example.firstspringboot.entity.Tutorial;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.firstspringboot.service.TutorialService;

import java.util.List;

@RestController
@RequestMapping("/api/tutorial")
@RequiredArgsConstructor
public class TutorialController {
    private final TutorialService service;
    @GetMapping("/{id}")
    public ResponseEntity<Tutorial> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.getById(id));
    }
    @GetMapping
    public ResponseEntity<List<Tutorial>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }
    @PostMapping
    public ResponseEntity<Tutorial> create(@RequestBody Tutorial tutorial){
        return ResponseEntity.ok(service.create(tutorial));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Tutorial> update(@PathVariable Long id, @RequestBody Tutorial tutorial){
        return ResponseEntity.ok(service.update(id, tutorial));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/test")
    public String test(){
        return "OK";
    }
}
