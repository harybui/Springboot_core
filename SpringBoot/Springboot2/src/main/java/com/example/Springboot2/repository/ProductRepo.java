package com.example.Springboot2.repository;

import com.example.Springboot2.entities.Product2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product2, Long> {
}
