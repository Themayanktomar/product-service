package com.example.productapi.dto;

import com.example.productapi.entity.Role;

public record UserResponseDTO(Long id, String username, String email, Role role) {
}
