package com.example.bookstore_api.controller;

import com.example.bookstore_api.common.ApiResponse;
import com.example.bookstore_api.dtos.AuthorDto;
import com.example.bookstore_api.dtos.CreateAuthorDto;
import com.example.bookstore_api.services.IAuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthorService authorService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AuthorDto>>> getAllAuthors(){
        return ResponseEntity.ok(ApiResponse.success(authorService.getAll()));
    }
    @PostMapping
    public ResponseEntity<ApiResponse<AuthorDto>> createAuthor(@RequestBody CreateAuthorDto request){
        AuthorDto created = authorService.createAuthor(request);
        return ResponseEntity.ok(ApiResponse.success(created));
    }
}
