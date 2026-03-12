package com.example.Springboot_day4.services;

import com.example.Springboot_day4.dtos.CreatePostDto;
import com.example.Springboot_day4.dtos.PostDto;
import com.example.Springboot_day4.entities.Post;
import com.example.Springboot_day4.entities.Tag;
import com.example.Springboot_day4.entities.User;
import com.example.Springboot_day4.repositories.PostRepository;
import com.example.Springboot_day4.repositories.TagRepository;
import com.example.Springboot_day4.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements IPostService{
    private final PostRepository postRepo;
    private final UserRepository userRepo;
    private final TagRepository tagRepo;
    public PostServiceImpl(PostRepository postRepo, UserRepository userRepo, TagRepository tagRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
        this.tagRepo = tagRepo;
    }
    @Override
    public PostDto creat(CreatePostDto request) {
       User user = userRepo.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
       List<Tag> tags = tagRepo.findAllById(request.getTagIds());
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUser(user);
        post.setTags(tags);

       Post saved = postRepo.save(post);
       return mapToDto(saved);
    }

    private PostDto mapToDto(Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setUserId(post.getUser().getId());
        dto.setTags(post.getTags().stream().map(Tag::getName).toList());
        return dto;
    }

    @Override
    public List<PostDto> getAll() {
        return postRepo.findAll().stream().map(this::mapToDto).toList();
    }

    @Override
    public PostDto getById(Long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        return mapToDto(post);
    }

    @Override
    public PostDto update(Long id, CreatePostDto request) {
        Post post = postRepo.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());

        Post saved = postRepo.save(post);
        return mapToDto(saved);
    }
    @Override
    public void delete(Long id) {
        if(!postRepo.existsById(id)){
            throw new RuntimeException("Post not found");
        }
        postRepo.deleteById(id);
    }
}
