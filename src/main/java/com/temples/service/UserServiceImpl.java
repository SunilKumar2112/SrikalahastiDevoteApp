package com.temples.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.temples.model.UserEntity;
import com.temples.util.JwtUtils;
import com.temples.util.PasswordUtilService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	com.temples.repo.userRepository userRepository;
	@Autowired
	PasswordUtilService passwordUtilService;
	
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	ModelMapper modelMapper;
	
	

	

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByUserName(userName)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userName));
		Collection<SimpleGrantedAuthority> authorites = new ArrayList<>();
		String userRoles = user.getRole();
		for (String role : userRoles.split(",")) {
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
			authorites.add(authority);
		}

		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				authorites);
	}

	
	@Override
    public Map<String, Object> validateToken(String authToken) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (authToken == null || !authToken.startsWith("Bearer ")) {
                throw new RuntimeException("Invalid token format");
            }
            
            String jwtToken = authToken.substring(7);
            String userName = jwtUtils.extractUsername(jwtToken);
            
            if (userName == null) {
                throw new RuntimeException("Invalid token: username not found");
            }
            
            UserDetails userDetails = loadUserByUsername(userName);
            boolean isValid = jwtUtils.validateToken(jwtToken, userDetails);
            
            result.put("valid", isValid);
            result.put("userName", userName);
            return result;
        } catch (Exception e) {
            result.put("valid", false);
            result.put("error", "Invalid token: " + e.getMessage());
            return result;
        }
    }

	
	private String extractUsername(Map<String, Object> tokenValidationResult) {
		if (tokenValidationResult != null && tokenValidationResult.get("valid").equals(true)) {
			return (String) tokenValidationResult.get("userName");
		} else {
			throw new UsernameNotFoundException("username is not found");
		}
	}

	

}
