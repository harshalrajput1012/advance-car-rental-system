package com.driveease.repository;

import com.driveease.entity.CarCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for CarCategory entity
 * Provides CRUD operations and custom queries for CarCategory
 */
@Repository
public interface CarCategoryRepository extends JpaRepository<CarCategory, Integer> {

    /**
     * Find category by category name
     * @param categoryName Category name
     * @return Optional containing CarCategory if found
     */
    Optional<CarCategory> findByCategoryName(String categoryName);
}
