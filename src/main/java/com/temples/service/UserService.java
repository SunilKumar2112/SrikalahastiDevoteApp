package com.temples.service;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {

	

	
	public Map<String, Object> validateToken(String authToken);
	
	
}
