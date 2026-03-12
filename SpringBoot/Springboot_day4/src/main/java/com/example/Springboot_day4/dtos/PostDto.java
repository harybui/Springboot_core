package com.example.Springboot_day4.dtos;

import lombok.Data;

import java.util.List;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private Long userId;
    private List<String> tags;
}
