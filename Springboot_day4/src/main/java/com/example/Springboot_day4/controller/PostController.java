package com.example.Springboot_day4.controller;

import com.example.Springboot_day4.dtos.CreatePostDto;
import com.example.Springboot_day4.dtos.PostDto;
import com.example.Springboot_day4.services.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    private final PostServiceImpl postService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAll(){
        return ResponseEntity.ok(postService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(postService.getById(id));
    }
    @PostMapping
    public ResponseEntity<PostDto> createNewPost(@RequestBody CreatePostDto createPostDto){
        return ResponseEntity.ok(postService.creat(createPostDto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @RequestBody CreatePostDto createPostDto){
        return ResponseEntity.ok(postService.update(id, createPostDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<PostDto> deletePost(@PathVariable Long id){
        postService.delete(id);
        return ResponseEntity.ok().build();
    }

}
