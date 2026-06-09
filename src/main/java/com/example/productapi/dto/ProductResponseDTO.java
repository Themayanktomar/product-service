package com.example.productapi.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponseDTO(
		Long id,
		String name,
		String description,
		BigDecimal price,
		boolean active,
		LocalDateTime createdAt,
		LocalDateTime updatedAt,
		String createdBy,
		String updatedBy) {
}
