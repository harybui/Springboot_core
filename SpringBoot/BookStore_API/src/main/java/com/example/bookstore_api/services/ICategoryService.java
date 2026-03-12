package com.example.bookstore_api.services;

import com.example.bookstore_api.dtos.BookDto;
import com.example.bookstore_api.dtos.CategoryDto;
import com.example.bookstore_api.dtos.CreateCategoryDto;

import java.util.List;

public interface ICategoryService {
    List<CategoryDto> getAll();
    CategoryDto create(CreateCategoryDto request);
    List<BookDto> getBookByCategory(Long categoryId);
}
