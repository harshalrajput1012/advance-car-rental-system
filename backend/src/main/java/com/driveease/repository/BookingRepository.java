package com.driveease.repository;

import com.driveease.entity.Booking;
import com.driveease.entity.Booking.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for Booking entity
 * Provides CRUD operations and custom queries for Booking
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    /**
     * Find all bookings by user
     * @param userId User ID
     * @return List of bookings for the user
     */
    List<Booking> findByUserId(Integer userId);

    /**
     * Find bookings by status
     * @param status Booking status
     * @return List of bookings with the specified status
     */
    List<Booking> findByStatus(BookingStatus status);

    /**
     * Find bookings by user and status
     * @param userId User ID
     * @param status Booking status
     * @return List of bookings for the user with the specified status
     */
    List<Booking> findByUserIdAndStatus(Integer userId, BookingStatus status);

    /**
     * Find bookings by car
     * @param carId Car ID
     * @return List of bookings for the car
     */
    List<Booking> findByCarId(Integer carId);

    /**
     * Check for overlapping bookings for a car
     * This query checks if there are any confirmed or pending bookings
     * that overlap with the given date range
     * 
     * @param carId Car ID
     * @param pickupDate Pickup date
     * @param returnDate Return date
     * @return Count of overlapping bookings
     */
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.car.id = :carId " +
           "AND b.status IN ('CONFIRMED', 'PENDING') " +
           "AND ((b.pickupDate <= :returnDate AND b.returnDate >= :pickupDate) OR " +
           "(b.pickupDate <= :pickupDate AND b.returnDate >= :returnDate) OR " +
           "(b.pickupDate >= :pickupDate AND b.returnDate <= :returnDate))")
    Long countOverlappingBookings(@Param("carId") Integer carId,
                                  @Param("pickupDate") LocalDateTime pickupDate,
                                  @Param("returnDate") LocalDateTime returnDate);

    /**
     * Find active bookings for a user
     * @param userId User ID
     * @return List of active bookings (PENDING, CONFIRMED)
     */
    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId AND b.status IN ('PENDING', 'CONFIRMED')")
    List<Booking> findActiveBookingsByUser(@Param("userId") Integer userId);
}
