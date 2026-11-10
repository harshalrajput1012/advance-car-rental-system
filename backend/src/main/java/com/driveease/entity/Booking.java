package com.driveease.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Booking Entity
 * Maps to the 'bookings' table in the database
 * Represents car rental bookings
 */
@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    @JsonIgnore
    private Car car;

    @Column(name = "pickup_location", nullable = false)
    private String pickupLocation;

    @Column(name = "dropoff_location", nullable = false)
    private String dropoffLocation;

    @Column(name = "pickup_date", nullable = false)
    private LocalDateTime pickupDate;

    @Column(name = "return_date", nullable = false)
    private LocalDateTime returnDate;

    @Column(name = "rental_days", nullable = false)
    private Integer rentalDays;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookingStatus status = BookingStatus.PENDING;

    @Column(name = "booking_date", updatable = false)
    private LocalDateTime bookingDate;

    @Column(name = "cancellation_reason", columnDefinition = "TEXT")
    private String cancellationReason;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @PrePersist
    protected void onCreate() {
        bookingDate = LocalDateTime.now();
    }

    /**
     * Enum for Booking Status
     */
    public enum BookingStatus {
        PENDING, CONFIRMED, REJECTED, CANCELLED, COMPLETED
    }
}
