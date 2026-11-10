package com.driveease.repository;

import com.driveease.entity.Payment;
import com.driveease.entity.Payment.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Payment entity
 * Provides CRUD operations and custom queries for Payment
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    /**
     * Find payment by booking
     * @param bookingId Booking ID
     * @return Optional containing Payment if found
     */
    Optional<Payment> findByBookingId(Integer bookingId);

    /**
     * Find payments by status
     * @param status Payment status
     * @return List of payments with the specified status
     */
    java.util.List<Payment> findByPaymentStatus(PaymentStatus status);
}
