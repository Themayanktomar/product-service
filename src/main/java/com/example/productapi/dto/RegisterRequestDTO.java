package com.example.productapi.dto;

import com.example.productapi.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
		@NotBlank(message = "Username cannot be blank")
		@Size(min = 3, max = 60, message = "Username must be between 3 and 60 characters")
		String username,

		@NotBlank(message = "Email cannot be blank")
		@Email(message = "Email must be valid")
		@Size(max = 120, message = "Email cannot exceed 120 characters")
		String email,

		@NotBlank(message = "Password cannot be blank")
		@Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
		String password,

		Role role) {
}
