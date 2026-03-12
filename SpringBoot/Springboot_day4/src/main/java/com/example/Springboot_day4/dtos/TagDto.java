package com.example.Springboot_day4.dtos;

import lombok.Data;

import java.util.List;

@Data
public class TagDto {
    private Long id;
    private String name;
    private List<PostDto> posts;
}
