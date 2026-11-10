package com.driveease.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security Configuration
 * Configures Spring Security for authentication and authorization
 * For this academic project, we'll use basic security without JWT
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF for REST APIs (stateless)
            .csrf(csrf -> csrf.disable())
            
            // Configure authorization rules
            .authorizeHttpRequests(auth -> auth
                // Allow all requests for now (for development and testing)
                // In production, restrict based on roles
                .anyRequest().permitAll()
            )
            
            // Configure HTTP Basic Authentication (optional)
            .httpBasic(org.springframework.security.config.Customizer.withDefaults());
        
        return http.build();
    }

    /**
     * Password encoder bean
     * Uses BCrypt for secure password hashing
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
