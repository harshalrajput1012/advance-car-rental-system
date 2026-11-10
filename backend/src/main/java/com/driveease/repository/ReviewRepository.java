package com.driveease.repository;

import com.driveease.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Review entity
 * Provides CRUD operations and custom queries for Review
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    /**
     * Find reviews by car
     * @param carId Car ID
     * @return List of reviews for the car
     */
    List<Review> findByCarId(Integer carId);

    /**
     * Find reviews by user
     * @param userId User ID
     * @return List of reviews by the user
     */
    List<Review> findByUserId(Integer userId);

    /**
     * Find review by user and car (unique constraint)
     * @param userId User ID
     * @param carId Car ID
     * @return Optional containing Review if found
     */
    java.util.Optional<Review> findByUserIdAndCarId(Integer userId, Integer carId);
}
