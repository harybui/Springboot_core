package com.example.bookstore_api.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {
    private Long id;
    private String name;
    private int bookCount;
    private List<String> books;
}
