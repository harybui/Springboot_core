package com.example.bookstore_api.controller;

import com.example.bookstore_api.common.ApiResponse;
import com.example.bookstore_api.dtos.BookDto;
import com.example.bookstore_api.dtos.CreatBookDto;
import com.example.bookstore_api.services.IBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final IBookService bookService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookDto>>> getAllBooks(){
        return ResponseEntity.ok(ApiResponse.success(bookService.getAll()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDto>> getBookById(@PathVariable Long id){
        return ResponseEntity.ok(ApiResponse.success(bookService.getById(id)));
    }
    @PostMapping
    public ResponseEntity<ApiResponse<BookDto>> createBook(@Valid @RequestBody CreatBookDto request){
        BookDto createdBook = bookService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(createdBook));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDto>> updateBook(@PathVariable Long id, @Valid @RequestBody CreatBookDto request){
        return ResponseEntity.ok(ApiResponse.success(bookService.update(id, request)));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDto>> deleteBook(@PathVariable Long id){
        bookService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
