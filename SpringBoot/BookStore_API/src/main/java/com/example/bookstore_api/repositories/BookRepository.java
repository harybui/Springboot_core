package com.example.bookstore_api.repositories;

import com.example.bookstore_api.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    //Select * from books where category_id=?
    List<Book> findByCategoryId(Long id);
}
