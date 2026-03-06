package com.example.Springboot_day4.services;

import com.example.Springboot_day4.dtos.CreateUserDto;
import com.example.Springboot_day4.dtos.UserDto;

import java.util.List;

public interface IUserService {
    UserDto create(CreateUserDto request);
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    UserDto updateUser(Long id, CreateUserDto request);
    void deleteUser(Long id);
}
