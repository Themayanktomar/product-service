package com.example.productapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.productapi.dto.ProductRequestDTO;
import com.example.productapi.exception.ResourceNotFoundException;
import com.example.productapi.mapper.ProductMapper;
import com.example.productapi.entity.Product;
import com.example.productapi.repository.ProductRepository;
import com.example.productapi.service.impl.ProductServiceImpl;
import com.example.productapi.util.SecurityUtil;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

	@Mock
	private ProductRepository productRepository;

	@Spy
	private ProductMapper productMapper;

	@Mock
	private SecurityUtil securityUtil;

	@InjectMocks
	private ProductServiceImpl productService;

	@Test
	void getAllProductsReturnsMappedDtos() {
		Product product = product(1L);
		when(productRepository.findAllByActiveTrue()).thenReturn(List.of(product));

		assertThat(productService.getAllProducts()).hasSize(1);
		assertThat(productService.getAllProducts().get(0).name()).isEqualTo("Laptop");
	}

	@Test
	void getProductByIdThrowsWhenMissing() {
		when(productRepository.findByIdAndActiveTrue(99L)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> productService.getProductById(99L))
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessageContaining("99");
	}

	@Test
	void createProductPersistsAndMapsResponse() {
		Product saved = product(10L);
		when(securityUtil.getCurrentUsername()).thenReturn("admin");
		when(productRepository.save(org.mockito.ArgumentMatchers.any(Product.class))).thenReturn(saved);

		var response = productService.createProduct(new ProductRequestDTO("Laptop", "Work machine", BigDecimal.TEN));

		assertThat(response.id()).isEqualTo(10L);
		assertThat(response.active()).isTrue();
		verify(productRepository).save(org.mockito.ArgumentMatchers.any(Product.class));
	}

	@Test
	void updateProductMutatesExistingEntity() {
		Product product = product(1L);
		when(securityUtil.getCurrentUsername()).thenReturn("admin");
		when(productRepository.findByIdAndActiveTrue(1L)).thenReturn(Optional.of(product));
		when(productRepository.save(product)).thenReturn(product);

		var response = productService.updateProduct(1L,
				new ProductRequestDTO("Phone", "Updated", BigDecimal.valueOf(99)));

		assertThat(response.name()).isEqualTo("Phone");
		assertThat(product.getDescription()).isEqualTo("Updated");
		assertThat(product.getUpdatedBy()).isEqualTo("admin");
	}

	@Test
	void deleteProductSoftDeletesExistingProduct() {
		Product product = product(1L);
		when(securityUtil.getCurrentUsername()).thenReturn("admin");
		when(productRepository.findByIdAndActiveTrue(1L)).thenReturn(Optional.of(product));

		productService.deleteProduct(1L);

		assertThat(product.isActive()).isFalse();
		assertThat(product.getUpdatedBy()).isEqualTo("admin");
		verify(productRepository).save(product);
	}

	private Product product(Long id) {
		Product product = new Product();
		product.setId(id);
		product.setName("Laptop");
		product.setDescription("Work machine");
		product.setPrice(BigDecimal.valueOf(1200));
		product.setActive(true);
		product.setCreatedAt(LocalDateTime.now());
		product.setUpdatedAt(LocalDateTime.now());
		product.setCreatedBy("creator");
		product.setUpdatedBy("updater");
		return product;
	}
}
