package com.example.bookstore_api.services;

import com.example.bookstore_api.dtos.AuthorDto;
import com.example.bookstore_api.dtos.CreateAuthorDto;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IAuthorService {
    List<AuthorDto> getAll();
    AuthorDto createAuthor(CreateAuthorDto request);
}
