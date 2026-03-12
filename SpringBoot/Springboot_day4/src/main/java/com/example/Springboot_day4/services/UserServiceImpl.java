package com.example.Springboot_day4.services;

import com.example.Springboot_day4.dtos.CreateUserDto;
import com.example.Springboot_day4.dtos.PostDto;
import com.example.Springboot_day4.dtos.UserDto;
import com.example.Springboot_day4.entities.User;
import com.example.Springboot_day4.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepo;

    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    private UserDto map(User user){
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        if (user.getPosts() != null) {
            dto.setPosts(user.getPosts().stream().map(post -> {
                PostDto postDto = new PostDto();
                postDto.setId(post.getId());
                postDto.setTitle(post.getTitle());
                postDto.setContent(post.getContent());
                postDto.setUserId(user.getId());
                if (post.getTags() != null) {
                    postDto.setTags(post.getTags().stream().map(tag -> tag.getName()).toList());
                }
                return postDto;
            }).toList());
        }
        return dto;
    }

    @Override
    public UserDto create(CreateUserDto request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        User saved = userRepo.save(user);
        return map(saved);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepo.findAll().stream().map(this::map).toList();
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
        return map(user);
    }

    @Override
    public UserDto updateUser(Long id, CreateUserDto request) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        User saved = userRepo.save(user);
        return map(saved);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepo.existsById(id)) {
            throw new RuntimeException("User with id " + id + " not found");
        }
        userRepo.deleteById(id);
    }
}
