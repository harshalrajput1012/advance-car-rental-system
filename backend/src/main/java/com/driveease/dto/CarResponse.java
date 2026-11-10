package com.driveease.dto;

import com.driveease.entity.Car;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for Car Response
 * Used to send car data to frontend without exposing internal fields
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarResponse {
    private Integer id;
    private String categoryName;
    private String brand;
    private String model;
    private Integer year;
    private String color;
    private String licensePlate;
    private String fuelType;
    private String transmission;
    private Integer seatingCapacity;
    private Double pricePerDay;
    private String imageUrl;
    private String description;
    private String features;
    private Boolean isAvailable;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Convert Car entity to CarResponse DTO
     */
    public static CarResponse fromEntity(Car car) {
        CarResponse response = new CarResponse();
        response.setId(car.getId());
        response.setCategoryName(car.getCategory() != null ? car.getCategory().getCategoryName() : null);
        response.setBrand(car.getBrand());
        response.setModel(car.getModel());
        response.setYear(car.getYear());
        response.setColor(car.getColor());
        response.setLicensePlate(car.getLicensePlate());
        response.setFuelType(car.getFuelType() != null ? car.getFuelType().name() : null);
        response.setTransmission(car.getTransmission() != null ? car.getTransmission().name() : null);
        response.setSeatingCapacity(car.getSeatingCapacity());
        response.setPricePerDay(car.getPricePerDay());
        response.setImageUrl(car.getImageUrl());
        response.setDescription(car.getDescription());
        response.setFeatures(car.getFeatures());
        response.setIsAvailable(car.getIsAvailable());
        response.setCreatedAt(car.getCreatedAt());
        response.setUpdatedAt(car.getUpdatedAt());
        return response;
    }
}
