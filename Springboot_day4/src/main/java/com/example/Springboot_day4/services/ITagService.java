package com.example.Springboot_day4.services;

import com.example.Springboot_day4.dtos.TagDto;
import com.example.Springboot_day4.entities.Tag;

import java.util.List;

public interface ITagService {
    TagDto create(TagDto request);
    List<TagDto> getAllTag();
    TagDto getTagById(Long id);
    TagDto updateTag(Long id, TagDto request);
    void deleteTag(Long id);
}
