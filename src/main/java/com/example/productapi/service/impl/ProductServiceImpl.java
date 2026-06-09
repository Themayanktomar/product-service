package com.example.productapi.service.impl;

import com.example.productapi.dto.ProductRequestDTO;
import com.example.productapi.dto.ProductResponseDTO;
import com.example.productapi.entity.Product;
import com.example.productapi.exception.ResourceNotFoundException;
import com.example.productapi.mapper.ProductMapper;
import com.example.productapi.repository.ProductRepository;
import com.example.productapi.service.ProductService;
import com.example.productapi.util.SecurityUtil;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final ProductMapper productMapper;
	private final SecurityUtil securityUtil;

	public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, SecurityUtil securityUtil) {
		this.productRepository = productRepository;
		this.productMapper = productMapper;
		this.securityUtil = securityUtil;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductResponseDTO> getAllProducts() {
		return productRepository.findAllByActiveTrue().stream().map(productMapper::toResponse).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public ProductResponseDTO getProductById(Long id) {
		return productMapper.toResponse(findProduct(id));
	}

	@Override
	public ProductResponseDTO createProduct(ProductRequestDTO request) {
		String username = securityUtil.getCurrentUsername();
		LocalDateTime now = LocalDateTime.now();
		Product product = productMapper.toEntity(request);
		product.setActive(true);
		product.setCreatedAt(now);
		product.setUpdatedAt(now);
		product.setCreatedBy(username);
		product.setUpdatedBy(username);
		product = productRepository.save(product);
		log.info("Created product id={}", product.getId());
		return productMapper.toResponse(product);
	}

	@Override
	public ProductResponseDTO updateProduct(Long id, ProductRequestDTO request) {
		String username = securityUtil.getCurrentUsername();
		Product product = findProduct(id);
		productMapper.updateEntity(product, request);
		product.setUpdatedAt(LocalDateTime.now());
		product.setUpdatedBy(username);
		Product saved = productRepository.save(product);
		log.info("Updated product id={}", saved.getId());
		return productMapper.toResponse(saved);
	}

	@Override
	public void deleteProduct(Long id) {
		String username = securityUtil.getCurrentUsername();
		Product product = findProduct(id);
		product.setActive(false);
		product.setUpdatedAt(LocalDateTime.now());
		product.setUpdatedBy(username);
		productRepository.save(product);
		log.info("Soft deleted product id={}", id);
	}

	private Product findProduct(Long id) {
		return productRepository.findByIdAndActiveTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
	}
}
