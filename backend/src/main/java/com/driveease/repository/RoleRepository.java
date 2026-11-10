package com.driveease.repository;

import com.driveease.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Role entity
 * Provides CRUD operations and custom queries for Role
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    /**
     * Find role by role name
     * @param roleName Role name (e.g., "CUSTOMER", "ADMIN")
     * @return Optional containing Role if found
     */
    Optional<Role> findByRoleName(String roleName);
}
