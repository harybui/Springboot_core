package com.example.Springboot_day4.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CreateUserDto {
    private String name;
    private String email;
}
