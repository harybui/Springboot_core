package com.example.Springboot_day4.controller;

import com.example.Springboot_day4.dtos.CreateTagDto;
import com.example.Springboot_day4.dtos.TagDto;
import com.example.Springboot_day4.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
    @GetMapping
    public ResponseEntity<List<TagDto>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTag());
    }
    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getTagById(@PathVariable Long id){
        return ResponseEntity.ok(tagService.getTagById(id));
    }
    @PostMapping
    public ResponseEntity<TagDto> createTag(@RequestBody  TagDto request){
        return ResponseEntity.ok(tagService.create(request));
    }
    @PutMapping("/{id}")
    public ResponseEntity<TagDto> updateTag(@PathVariable Long id, @RequestBody TagDto request){
        return ResponseEntity.ok(tagService.updateTag(id, request));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<TagDto> deleteTag(@PathVariable Long id){
        tagService.deleteTag(id);
        return ResponseEntity.ok().build();
    }
}
