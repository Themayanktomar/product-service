package com.example.productapi.service;

import com.example.productapi.dto.AuthResponseDTO;
import com.example.productapi.dto.LoginRequestDTO;
import com.example.productapi.dto.RegisterRequestDTO;
import com.example.productapi.dto.UserResponseDTO;

public interface AuthService {

	UserResponseDTO register(RegisterRequestDTO request);

	AuthResponseDTO login(LoginRequestDTO request);
}
