package com.driveease.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for Booking Request
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {

    @NotNull(message = "Car ID is required")
    private Integer carId;

    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotBlank(message = "Pickup location is required")
    private String pickupLocation;

    @NotBlank(message = "Drop-off location is required")
    private String dropoffLocation;

    @NotNull(message = "Pickup date is required")
    private LocalDateTime pickupDate;

    @NotNull(message = "Return date is required")
    @Future(message = "Return date must be in the future")
    private LocalDateTime returnDate;
}
