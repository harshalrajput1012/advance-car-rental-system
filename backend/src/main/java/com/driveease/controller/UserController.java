package com.driveease.controller;

import com.driveease.dto.ApiResponse;
import com.driveease.entity.User;
import com.driveease.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * User Controller
 * Handles user-related operations
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    /**
     * Get user by ID
     * GET /api/users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Integer id) {
        try {
            User user = userService.findById(id);
            // Don't return password in response
            user.setPassword(null);
            return ResponseEntity.ok(ApiResponse.success(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * Get all users (Admin only)
     * GET /api/users
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        // Remove passwords from response
        users.forEach(user -> user.setPassword(null));
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    /**
     * Update user profile
     * PUT /api/users/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(
            @PathVariable Integer id,
            @RequestBody User userDetails) {
        try {
            User updatedUser = userService.updateUser(id, userDetails);
            updatedUser.setPassword(null);
            return ResponseEntity.ok(ApiResponse.success(updatedUser, "Profile updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * Change password
     * PUT /api/users/{id}/password
     */
    @PutMapping("/{id}/password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @PathVariable Integer id,
            @RequestBody Map<String, String> request) {
        try {
            String oldPassword = request.get("oldPassword");
            String newPassword = request.get("newPassword");
            userService.changePassword(id, oldPassword, newPassword);
            return ResponseEntity.ok(ApiResponse.success(null, "Password changed successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * Deactivate user account (Admin only)
     * DELETE /api/users/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deactivateUser(@PathVariable Integer id) {
        try {
            userService.deactivateUser(id);
            return ResponseEntity.ok(ApiResponse.success(null, "User deactivated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}
