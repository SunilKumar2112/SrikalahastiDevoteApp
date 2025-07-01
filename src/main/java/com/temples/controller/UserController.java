package com.temples.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.temples.dto.TokenResponse;
import com.temples.dto.UserDto;
import com.temples.service.UserService;
import com.temples.util.JwtUtils;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/bfsi/user-management")
@CrossOrigin("*")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	AuthenticationManager authenticationManager;

	
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TokenResponse> login(@RequestBody UserDto userRequest) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userRequest.getUserName(), userRequest.getPassword()));
			String generatedToken = jwtUtils.generateToken(userRequest.getUserName());
			

			return new ResponseEntity<>(new TokenResponse(generatedToken), HttpStatus.OK);
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new TokenResponse("Invalid username or password"));
		}
	}

	@Operation(description = "Validate JWT token")
	@GetMapping(value = "/user/token/validate")
	public ResponseEntity<Map<String, Object>> validateToken(@RequestHeader("Authorization") String authToken) {
		Map<String, Object> result = userService.validateToken(authToken);
		if (null != result && result.get("valid") != null && result.get("valid").equals(true)) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
	}

	

}
