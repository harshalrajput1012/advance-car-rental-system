package com.driveease.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * CarCategory Entity
 * Maps to the 'car_categories' table in the database
 * Represents car categories (SUV, Sedan, Hatchback, etc.)
 */
@Entity
@Table(name = "car_categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "category_name", nullable = false, unique = true, length = 50)
    private String categoryName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
