package com.driveease.repository;

import com.driveease.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entity
 * Provides CRUD operations and custom queries for User
 * 
 * JpaRepository extends:
 * - CrudRepository: Basic CRUD operations
 * - PagingAndSortingRepository: Pagination and sorting
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Find user by email
     * @param email User's email
     * @return Optional containing User if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if email exists
     * @param email Email to check
     * @return true if email exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Find user by mobile number
     * @param mobileNumber User's mobile number
     * @return Optional containing User if found
     */
    Optional<User> findByMobileNumber(String mobileNumber);
}
