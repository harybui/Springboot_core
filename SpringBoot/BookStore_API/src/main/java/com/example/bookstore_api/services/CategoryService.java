package com.example.bookstore_api.services;

import com.example.bookstore_api.dtos.BookDto;
import com.example.bookstore_api.dtos.CategoryDto;
import com.example.bookstore_api.dtos.CreateCategoryDto;
import com.example.bookstore_api.entities.Author;
import com.example.bookstore_api.entities.Book;
import com.example.bookstore_api.entities.Category;
import com.example.bookstore_api.exception.AlreadyExistsException;
import com.example.bookstore_api.exception.NotFoundExecption;
import com.example.bookstore_api.repositories.BookRepository;
import com.example.bookstore_api.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
    private final CategoryRepository cateRepo;
    private final BookRepository bookRepo;


    @Override
    public List<CategoryDto> getAll() {
        return cateRepo.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public CategoryDto create(CreateCategoryDto request) {
        if(cateRepo.existsByNameIgnoreCase(request.getName())){
            throw new AlreadyExistsException(
                    "Category with name " + request.getName() + " already exists"
            );
        }
        Category category = new Category();
        category.setName(request.getName());

        Category saved = cateRepo.save(category);
        return mapToDto(saved);
    }

    @Override
    public List<BookDto> getBookByCategory(Long categoryId) {
        if(!cateRepo.existsById(categoryId)){
            throw new NotFoundExecption(
                    "Category with id " + categoryId + " does not exist"
            );
        }
        return bookRepo.findByCategoryId(categoryId)
                .stream()
                .map(book -> mapBookToDto(book))
                .toList();
    }
    private CategoryDto mapToDto(Category category){
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());

        if(category.getBooks() != null){
            dto.setBookCount(category.getBooks().size());
            dto.setBooks(
                    category.getBooks().stream()
                            .map(Book::getTitle)
                            .toList()
            );
        }
        else {
            dto.setBookCount(0);
            dto.setBooks(List.of());
        }
        return dto;
    }
    private BookDto mapBookToDto(com.example.bookstore_api.entities.Book book){
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setPrice(book.getPrice());
        dto.setDescription(book.getDescription());
        dto.setCategoryName(book.getCategory() != null ? book.getCategory().getName() : null);
        dto.setAuthors(
                book.getAuthors() == null ? List.of() :
                        book.getAuthors().stream().map(Author::getName).toList()
        );
        return dto;
    }
}
