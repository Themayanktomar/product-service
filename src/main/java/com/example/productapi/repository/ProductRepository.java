package com.example.productapi.repository;

import com.example.productapi.entity.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findAllByActiveTrue();

	Optional<Product> findByIdAndActiveTrue(Long id);
}
