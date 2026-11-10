package com.driveease.controller;

import com.driveease.dto.ApiResponse;
import com.driveease.dto.LoginRequest;
import com.driveease.dto.RegisterRequest;
import com.driveease.entity.User;
import com.driveease.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication Controller
 * Handles user registration and login
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Register a new user
     * POST /api/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> registerUser(@Valid @RequestBody RegisterRequest request) {
        try {
            User user = userService.registerUser(request);
            // Don't return password in response
            user.setPassword(null);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(user, "User registered successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * User login
     * POST /api/auth/login
     * Note: For this academic project, we'll return user data without JWT
     * In production, use JWT tokens
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<User>> loginUser(@Valid @RequestBody LoginRequest request) {
        try {
            var userOptional = userService.findByEmail(request.getEmail());
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.error("Invalid email or password"));
            }

            User user = userOptional.get();
            // Verify password with BCrypt
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.error("Invalid email or password"));
            }

            if (!user.getIsActive()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ApiResponse.error("Account is deactivated"));
            }

            // Don't return password in response
            user.setPassword(null);
            return ResponseEntity.ok(ApiResponse.success(user, "Login successful"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Login failed: " + e.getMessage()));
        }
    }
}
