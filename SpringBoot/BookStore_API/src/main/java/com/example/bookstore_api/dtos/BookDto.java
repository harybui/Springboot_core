package com.example.bookstore_api.dtos;

import lombok.Data;

import java.util.List;

@Data
public class BookDto {
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String categoryName;
    private List<String> authors;
}
