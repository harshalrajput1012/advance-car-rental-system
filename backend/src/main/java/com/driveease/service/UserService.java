package com.driveease.service;

import com.driveease.dto.RegisterRequest;
import com.driveease.entity.Role;
import com.driveease.entity.User;
import com.driveease.exception.ResourceNotFoundException;
import com.driveease.repository.RoleRepository;
import com.driveease.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class for User operations
 * Contains business logic related to user management
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Register a new user
     */
    public User registerUser(RegisterRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // Check if passwords match
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        // Get CUSTOMER role
        Role customerRole = roleRepository.findByRoleName("CUSTOMER")
                .orElseThrow(() -> new ResourceNotFoundException("Role", "role_name", "CUSTOMER"));

        // Create new user
        User user = new User();
        user.setRole(customerRole);
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setMobileNumber(request.getMobileNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAddress(request.getAddress());
        user.setCity(request.getCity());
        user.setState(request.getState());
        user.setPostalCode(request.getPostalCode());
        user.setCountry(request.getCountry());
        user.setIsActive(true);

        return userRepository.save(user);
    }

    /**
     * Find user by email
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Find user by ID
     */
    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
    }

    /**
     * Get all users (for admin)
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Update user profile
     */
    public User updateUser(Integer userId, User userDetails) {
        User user = findById(userId);
        
        user.setFullName(userDetails.getFullName());
        user.setMobileNumber(userDetails.getMobileNumber());
        user.setAddress(userDetails.getAddress());
        user.setCity(userDetails.getCity());
        user.setState(userDetails.getState());
        user.setPostalCode(userDetails.getPostalCode());
        user.setCountry(userDetails.getCountry());

        return userRepository.save(user);
    }

    /**
     * Change user password
     */
    public void changePassword(Integer userId, String oldPassword, String newPassword) {
        User user = findById(userId);
        
        // Verify old password (in real app, use passwordEncoder.matches())
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    /**
     * Deactivate user account
     */
    public void deactivateUser(Integer userId) {
        User user = findById(userId);
        user.setIsActive(false);
        userRepository.save(user);
    }
}
