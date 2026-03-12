package com.example.Springboot_day4.services;

import com.example.Springboot_day4.dtos.CreatePostDto;
import com.example.Springboot_day4.dtos.PostDto;

import java.util.List;

public interface IPostService {
    PostDto creat(CreatePostDto request);
    List<PostDto> getAll();
    PostDto getById(Long id);
    PostDto update(Long id, CreatePostDto request);
    void delete(Long id);
}
