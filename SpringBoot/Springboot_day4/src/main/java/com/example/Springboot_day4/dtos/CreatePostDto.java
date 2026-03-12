package com.example.Springboot_day4.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CreatePostDto {
    private Long userId;
    private String title;
    private String content;
    private List<Long> tagIds;
}
