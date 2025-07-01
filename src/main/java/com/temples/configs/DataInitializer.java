package com.temples.configs;

import com.temples.model.UserEntity;
import com.temples.repo.userRepository;
import com.temples.util.PasswordUtilService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Autowired
    private userRepository userRepository;

    @Autowired
    private PasswordUtilService passwordEncoder;

    @Bean
    public CommandLineRunner initAdminUser() {
        return args -> {
            // Check if admin user already exists
            if (!userRepository.findByUserName("admin").isPresent()) {
                UserEntity admin = new UserEntity();
                admin.setUserName("admin");
                admin.setPassword(passwordEncoder.hash("admin")); // Encode password
                admin.setRole("ROLE_ADMIN"); // Set role
                userRepository.save(admin);
                System.out.println("Admin user created with username: admin, password: admin, role: ROLE_ADMIN");
            } else {
                System.out.println("Admin user already exists");
            }
        };
    }
}