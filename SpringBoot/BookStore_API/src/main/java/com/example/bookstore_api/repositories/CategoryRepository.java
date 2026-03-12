package com.example.bookstore_api.repositories;

import com.example.bookstore_api.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    //Select * from categories where name=?
    boolean existsByName(String name);
    //Select * from categories where name =? (ignoring case)
    boolean existsByNameIgnoreCase(String name);
}
