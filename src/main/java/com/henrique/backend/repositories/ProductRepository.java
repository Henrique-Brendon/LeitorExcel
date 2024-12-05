package com.henrique.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.henrique.backend.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
