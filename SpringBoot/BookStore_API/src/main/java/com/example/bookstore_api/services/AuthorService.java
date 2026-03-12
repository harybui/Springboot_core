package com.example.bookstore_api.services;

import com.example.bookstore_api.dtos.AuthorDto;
import com.example.bookstore_api.dtos.CreateAuthorDto;
import com.example.bookstore_api.entities.Author;
import com.example.bookstore_api.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService implements IAuthorService{
    private final AuthorRepository authRepo;
private AuthorDto mapToDto(Author author){
    AuthorDto dto = new AuthorDto();
    dto.setId(author.getId());
    dto.setName(author.getName());
    return dto;
}
    @Override
    public List<AuthorDto> getAll() {
        return authRepo.findAll().stream().map(this::mapToDto).toList();
    }

    @Override
    public AuthorDto createAuthor(CreateAuthorDto request) {
        Author author = new Author();
        author.setName(request.getName());
        return mapToDto(authRepo.save(author));
    }

}
