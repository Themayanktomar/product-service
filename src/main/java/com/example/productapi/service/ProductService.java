package com.example.productapi.service;

import com.example.productapi.dto.ProductRequestDTO;
import com.example.productapi.dto.ProductResponseDTO;
import java.util.List;

public interface ProductService {

	List<ProductResponseDTO> getAllProducts();

	ProductResponseDTO getProductById(Long id);

	ProductResponseDTO createProduct(ProductRequestDTO request);

	ProductResponseDTO updateProduct(Long id, ProductRequestDTO request);

	void deleteProduct(Long id);
}
