package com.example.Springboot2.services;

import com.example.Springboot2.entities.Product2;
import com.example.Springboot2.repository.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepo repo;
    public ProductService(ProductRepo repo) {
        this.repo = repo;
    }
    public List<Product2> getAll(){
        return repo.findAll();
    }
    public Product2 getById(Long id){
        return repo.findById(id).orElse(null);
    }
    public Product2 create(Product2 product2){

        return repo.save(product2);
    }
    public Product2 update(Product2 product2){
        if(repo.existsById(product2.getId())){
            return repo.save(product2);
        }
        throw new RuntimeException("Product not found");
    }
    public String delete(Long id){
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return "complete delete";
        }
        throw new RuntimeException("Product not found");
    }
}
