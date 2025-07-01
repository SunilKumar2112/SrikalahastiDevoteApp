package com.temples.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.temples.dto.DevoteeDto;
import com.temples.service.DevoteeService;
import com.temples.service.UserService;

@RestController
@RequestMapping("/api/devotees")
@CrossOrigin("*")
public class DevoteeController {

    @Autowired
    private DevoteeService devoteeService; // Note: Consider renaming to devoteeService for clarity

    @Autowired
    private UserService userService; // Added to validate authToken

    @PostMapping
    public ResponseEntity<?> createDevotees(
            @RequestBody List<DevoteeDto> devoteeDtos,
            @RequestHeader(value = "Authorization", required = true) String authToken) {
        // Validate the token
        Map<String, Object> tokenValidationResult = userService.validateToken(authToken);
        if (!Boolean.TRUE.equals(tokenValidationResult.get("valid"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or missing token: " + tokenValidationResult.get("error"));
        }
        System.out.println(devoteeDtos);
        // Process each devotee
       List<DevoteeDto> savedDevotees = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        for (DevoteeDto devoteeDto : devoteeDtos) {
            try {
                DevoteeDto savedDevotee = devoteeService.createDevotee(devoteeDto);
                savedDevotees.add(savedDevotee);
            } catch (IllegalArgumentException e) {
                errors.add("Error for devotee with phone " + devoteeDto.getPhoneNumber() + ": " + e.getMessage());
            } catch (Exception e) {
                errors.add("Unexpected error for devotee with phone " + devoteeDto.getPhoneNumber() + ": " + e.getMessage());
            }
        }

        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        return ResponseEntity.ok(savedDevotees);
    }
}