package com.example.productapi.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record ProductRequestDTO(
		@NotBlank(message = "Name cannot be blank")
		@Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
		String name,

		@Size(max = 500, message = "Description cannot exceed 500 characters")
		String description,

		@NotNull(message = "Price is required")
		@DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
		BigDecimal price) {
}
