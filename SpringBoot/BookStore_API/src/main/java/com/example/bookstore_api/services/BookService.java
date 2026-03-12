package com.example.bookstore_api.services;

import com.example.bookstore_api.dtos.BookDto;
import com.example.bookstore_api.dtos.CreatBookDto;
import com.example.bookstore_api.entities.Author;
import com.example.bookstore_api.entities.Book;
import com.example.bookstore_api.entities.Category;
import com.example.bookstore_api.exception.NotFoundExecption;
import com.example.bookstore_api.repositories.AuthorRepository;
import com.example.bookstore_api.repositories.BookRepository;
import com.example.bookstore_api.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {
    private final BookRepository bookRepo;
    private final AuthorRepository authRepo;
    private final CategoryRepository cateRepo;

    private BookDto mapToDto(Book book){
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setPrice(book.getPrice());
        dto.setDescription(book.getDescription());
        dto.setCategoryName(book.getCategory().getName());
        dto.setAuthors(
                book.getAuthors()== null ? List.of():
                        book.getAuthors().stream()
                                .map(Author::getName)
                                .toList()
        );
        return dto;
    }

    @Override
    public List<BookDto> getAll() {
        return bookRepo.findAll()
                .stream().map(this::mapToDto).toList();
    }

    @Override
    public BookDto getById(Long id) {
        Book book = bookRepo.findById(id).orElseThrow(() -> new NotFoundExecption("Book not found"));
        return mapToDto(book);
    }

    @Override
    public BookDto create(CreatBookDto request) {
        //check categoryId is not null
        Category category = cateRepo.findById(request.getCategoryId()).orElseThrow(() -> new NotFoundExecption("Category not found"));
        List<Author> authors = List.of();
        if(request.getAuthorIds() != null && !request.getAuthorIds().isEmpty()){
            authors = authRepo.findAllById(request.getAuthorIds());
            if(authors.size() != request.getAuthorIds().size()){
                throw  new NotFoundExecption("Authors not found");
            }
        }
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setPrice(request.getPrice());
        book.setDescription(request.getDescription());
        book.setCategory(category);
        book.setAuthors(authors);
        return mapToDto(bookRepo.save(book));
    }

    @Override
    public BookDto update(Long id, CreatBookDto request) {
        //check bookId is not null
        Book book = bookRepo.findById(id).orElseThrow(() -> new NotFoundExecption("Book not found"));
        Category category = cateRepo.findById(request.getCategoryId()).orElseThrow(() -> new NotFoundExecption("Category not found"));
        List<Author> authors = List.of();
        if(request.getAuthorIds() != null && !request.getAuthorIds().isEmpty()){
            authors = authRepo.findAllById(request.getAuthorIds());
        }
        book.setTitle(request.getTitle());
        book.setPrice(request.getPrice());
        book.setDescription(request.getDescription());
        book.setCategory(category);
        book.setAuthors(authors);
        return mapToDto(bookRepo.save(book));
    }

    @Override
    public void delete(Long id) {
        //check not null
        if(!bookRepo.existsById(id)){
            throw new NotFoundExecption("Book not found");
        }
        bookRepo.deleteById(id);
    }
}
