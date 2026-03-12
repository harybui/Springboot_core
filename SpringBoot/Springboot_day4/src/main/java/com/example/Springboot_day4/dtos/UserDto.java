package com.example.Springboot_day4.dtos;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private List<PostDto> posts;
}
