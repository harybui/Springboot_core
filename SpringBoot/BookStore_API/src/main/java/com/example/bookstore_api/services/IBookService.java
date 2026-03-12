package com.example.bookstore_api.services;

import com.example.bookstore_api.dtos.BookDto;
import com.example.bookstore_api.dtos.CreatBookDto;

import java.util.List;

public interface IBookService {
    List<BookDto> getAll();
    BookDto getById(Long id);
    BookDto create(CreatBookDto request);
    BookDto update(Long id, CreatBookDto request);
    void delete(Long id);
}
