package com.example.nurgalykovArman.repositories;

import com.example.nurgalykovArman.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
