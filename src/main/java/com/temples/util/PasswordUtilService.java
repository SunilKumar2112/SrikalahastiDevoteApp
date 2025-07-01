package com.temples.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtilService {
	private final PasswordEncoder passwordEncoder;

	public PasswordUtilService(@Value("${password.bcrypt.strength:12}") int strength) {
		if (strength < 4 || strength > 31) {
			throw new IllegalArgumentException("BCrypt strength must be 4-31");
		}
		this.passwordEncoder = new BCryptPasswordEncoder(strength);
	}

	public String hash(String password) {
		if (password == null || password.isEmpty()) {
			throw new IllegalArgumentException("Password cannot be empty");
		}
		return passwordEncoder.encode(password);
	}

	public boolean verify(String password, String hash) {
		if (password == null || hash == null) {
			return false;
		}
		return passwordEncoder.matches(password, hash);
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

}
