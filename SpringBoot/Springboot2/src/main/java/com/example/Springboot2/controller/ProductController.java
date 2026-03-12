package com.example.Springboot2.controller;

import com.example.Springboot2.entities.Product2;
import com.example.Springboot2.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
private final ProductService service;
public ProductController(ProductService service) {
	this.service = service;
}
@GetMapping("/{id}")
public ResponseEntity<Product2> getById(@PathVariable Long id){
    Product2 pro = service.getById(id);
    if(pro != null){
        return ResponseEntity.ok(pro);
    } else {
        return ResponseEntity.notFound().build();
    }
}
@GetMapping
public ResponseEntity<List<Product2>> getAll(){
    return ResponseEntity.ok(service.getAll());
}
@PostMapping
    public ResponseEntity<Product2> createNew(@Valid @RequestBody Product2 product){
    try{
        Product2 pro = service.create(product);
        return   ResponseEntity.ok(pro);
    }catch (Exception e){
        return ResponseEntity.badRequest().build();
    }
}
@PutMapping("/{id}")
    public ResponseEntity<Product2> update( @PathVariable Long id,@RequestBody Product2 product2){
    product2.setId(id);
    return ResponseEntity.ok(service.update(product2));
}
@DeleteMapping("/{id}")
    public ResponseEntity<Product2> delete(@PathVariable Long id){
    service.delete(id);
    return ResponseEntity.ok().build();
}
}
