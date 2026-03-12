package com.example.Springboot_day4.services;

import com.example.Springboot_day4.dtos.PostDto;
import com.example.Springboot_day4.dtos.TagDto;
import com.example.Springboot_day4.entities.Tag;
import com.example.Springboot_day4.repositories.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService implements ITagService {
    private final TagRepository tagRepo;

    public TagService(TagRepository tagRepo) {
        this.tagRepo = tagRepo;
    }

    private TagDto map(Tag tag) {
        TagDto tagDto = new TagDto();
        tagDto.setId(tag.getId());
        tagDto.setName(tag.getName());
        if (tag.getPosts() != null) {
            tagDto.setPosts(tag.getPosts().stream().map(post -> {
                PostDto postDto = new PostDto();
                postDto.setId(post.getId());
                postDto.setTitle(post.getTitle());
                postDto.setContent(post.getContent());
                if (post.getUser() != null)
                    postDto.setUserId(post.getUser().getId());
                if (post.getTags() != null) {
                    postDto.setTags(post.getTags().stream().map(t -> t.getName()).toList());
                }
                return postDto;
            }).toList());
        }
        return tagDto;
    }

    @Override
    public TagDto create(TagDto request) {
        Tag tag = new Tag();
        tag.setName(request.getName());
        return map(tagRepo.save(tag));
    }

    @Override
    public List<TagDto> getAllTag() {
        return tagRepo.findAll().stream().map(this::map).toList();
    }

    @Override
    public TagDto getTagById(Long id) {
        Tag tag = tagRepo.findById(id).orElseThrow(() -> new RuntimeException("Tag not found"));
        return map(tag);
    }

    @Override
    public TagDto updateTag(Long id, TagDto request) {
        Tag tag = tagRepo.findById(id).orElseThrow(() -> new RuntimeException("Tag not found"));
        tag.setName(request.getName());
        Tag saved = tagRepo.save(tag);
        return map(saved);
    }

    @Override
    public void deleteTag(Long id) {
        if (!tagRepo.existsById(id)) {
            throw new RuntimeException("Tag not found");
        }
        tagRepo.deleteById(id);
    }
}
