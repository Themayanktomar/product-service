package com.example.productapi.dto;

public record AuthResponseDTO(String accessToken, String tokenType, long expiresInMs) {
}
