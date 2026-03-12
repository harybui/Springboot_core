package com.example.bookstore_api.repositories;

import com.example.bookstore_api.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long> {
}
