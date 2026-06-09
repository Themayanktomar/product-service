package com.example.productapi.service.impl;

import com.example.productapi.dto.AuthResponseDTO;
import com.example.productapi.dto.LoginRequestDTO;
import com.example.productapi.dto.RegisterRequestDTO;
import com.example.productapi.dto.UserResponseDTO;
import com.example.productapi.entity.Role;
import com.example.productapi.entity.User;
import com.example.productapi.exception.DuplicateResourceException;
import com.example.productapi.repository.UserRepository;
import com.example.productapi.security.UserPrincipal;
import com.example.productapi.service.AuthService;
import com.example.productapi.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public UserResponseDTO register(RegisterRequestDTO request) {
		log.info("Registration attempt username={}", request.username());
		if (userRepository.existsByUsername(request.username())) {
			throw new DuplicateResourceException("Username is already registered");
		}
		if (userRepository.existsByEmail(request.email())) {
			throw new DuplicateResourceException("Email is already registered");
		}

		User user = new User();
		user.setUsername(request.username().trim());
		user.setEmail(request.email().trim().toLowerCase());
		user.setPassword(passwordEncoder.encode(request.password()));
		user.setRole(request.role() == null ? Role.ROLE_USER : request.role());
		User saved = userRepository.save(user);
		return new UserResponseDTO(saved.getId(), saved.getUsername(), saved.getEmail(), saved.getRole());
	}

	@Override
	@Transactional(readOnly = true)
	public AuthResponseDTO login(LoginRequestDTO request) {
		log.info("Login attempt username={}", request.username());
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.username(), request.password()));
		User user = userRepository.findByUsername(request.username())
				.orElseThrow(() -> new DuplicateResourceException("Invalid credentials"));
		String token = jwtUtil.generateToken(UserPrincipal.from(user));
		return new AuthResponseDTO(token, "Bearer", jwtUtil.getExpirationMs());
	}
}
