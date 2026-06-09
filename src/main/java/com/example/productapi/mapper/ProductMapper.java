package com.example.productapi.mapper;

import com.example.productapi.dto.ProductRequestDTO;
import com.example.productapi.dto.ProductResponseDTO;
import com.example.productapi.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

	public Product toEntity(ProductRequestDTO request) {
		Product product = new Product();
		updateEntity(product, request);
		return product;
	}

	public ProductResponseDTO toResponse(Product product) {
		return new ProductResponseDTO(
				product.getId(),
				product.getName(),
				product.getDescription(),
				product.getPrice(),
				product.isActive(),
				product.getCreatedAt(),
				product.getUpdatedAt(),
				product.getCreatedBy(),
				product.getUpdatedBy());
	}

	public void updateEntity(Product product, ProductRequestDTO request) {
		product.setName(request.name().trim());
		product.setDescription(request.description());
		product.setPrice(request.price());
	}
}
