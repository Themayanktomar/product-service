package com.example.productapi.util;

import com.example.productapi.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

	public String getCurrentUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal userPrincipal)) {
			throw new IllegalStateException("Authenticated user context is required");
		}
		return userPrincipal.getUsername();
	}
}
