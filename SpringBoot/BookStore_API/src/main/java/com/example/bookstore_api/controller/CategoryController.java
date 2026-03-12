package com.example.bookstore_api.controller;

import com.example.bookstore_api.common.ApiResponse;
import com.example.bookstore_api.dtos.BookDto;
import com.example.bookstore_api.dtos.CategoryDto;
import com.example.bookstore_api.dtos.CreateCategoryDto;
import com.example.bookstore_api.services.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getAllCategories(){
        List<CategoryDto> categories = categoryService.getAll();
        return ResponseEntity.ok(
                ApiResponse.success(categories)
        );
    }
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryDto>> createCategory(@Valid @RequestBody CreateCategoryDto request){
        CategoryDto created = categoryService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(created));
    }
    @GetMapping("/{id}/books")
    public ResponseEntity<ApiResponse<List<BookDto>>> getBookByCategory(@PathVariable Long id){
        List<BookDto> books = categoryService.getBookByCategory(id);
        return ResponseEntity.ok(ApiResponse.success(books));
    }
}
