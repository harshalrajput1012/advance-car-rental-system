package com.driveease.service;

import com.driveease.dto.BookingRequest;
import com.driveease.entity.Booking;
import com.driveease.entity.Booking.BookingStatus;
import com.driveease.entity.Car;
import com.driveease.entity.User;
import com.driveease.exception.BookingConflictException;
import com.driveease.exception.ResourceNotFoundException;
import com.driveease.repository.BookingRepository;
import com.driveease.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for Booking operations
 * Contains business logic related to booking management
 */
@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final CarRepository carRepository;

    /**
     * Create a new booking
     */
    public Booking createBooking(BookingRequest request) {
        // Validate user exists
        User user = userService.findById(request.getUserId());

        // Validate car exists and is available
        Car car = carRepository.findById(request.getCarId())
                .orElseThrow(() -> new ResourceNotFoundException("Car", request.getCarId()));
        if (!car.getIsAvailable()) {
            throw new RuntimeException("Car is not available for booking");
        }

        // Validate dates
        if (request.getReturnDate().isBefore(request.getPickupDate())) {
            throw new RuntimeException("Return date must be after pickup date");
        }

        // Calculate rental days
        long rentalDays = Duration.between(request.getPickupDate(), request.getReturnDate()).toDays();
        if (rentalDays <= 0) {
            throw new RuntimeException("Minimum rental period is 1 day");
        }

        // Check for overlapping bookings
        Long overlappingCount = bookingRepository.countOverlappingBookings(
                request.getCarId(),
                request.getPickupDate(),
                request.getReturnDate()
        );

        if (overlappingCount > 0) {
            throw new BookingConflictException();
        }

        // Calculate total amount
        Double totalAmount = car.getPricePerDay() * rentalDays;

        // Create booking
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setCar(car);
        booking.setPickupLocation(request.getPickupLocation());
        booking.setDropoffLocation(request.getDropoffLocation());
        booking.setPickupDate(request.getPickupDate());
        booking.setReturnDate(request.getReturnDate());
        booking.setRentalDays((int) rentalDays);
        booking.setTotalAmount(totalAmount);
        booking.setStatus(BookingStatus.PENDING);

        return bookingRepository.save(booking);
    }

    /**
     * Get booking by ID
     */
    public Booking getBookingById(Integer id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", id));
    }

    /**
     * Get all bookings for a user
     */
    public List<Booking> getUserBookings(Integer userId) {
        return bookingRepository.findByUserId(userId);
    }

    /**
     * Get active bookings for a user
     */
    public List<Booking> getActiveBookings(Integer userId) {
        return bookingRepository.findActiveBookingsByUser(userId);
    }

    /**
     * Get all bookings (for admin)
     */
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    /**
     * Get bookings by status
     */
    public List<Booking> getBookingsByStatus(BookingStatus status) {
        return bookingRepository.findByStatus(status);
    }

    /**
     * Cancel booking
     */
    public void cancelBooking(Integer bookingId, String reason) {
        Booking booking = getBookingById(bookingId);

        // Can only cancel PENDING or CONFIRMED bookings
        if (booking.getStatus() != BookingStatus.PENDING && 
            booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new RuntimeException("Cannot cancel booking with status: " + booking.getStatus());
        }

        booking.setStatus(BookingStatus.CANCELLED);
        booking.setCancellationReason(reason);
        booking.setCancelledAt(LocalDateTime.now());

        bookingRepository.save(booking);
    }

    /**
     * Approve booking (for admin)
     */
    public void approveBooking(Integer bookingId) {
        Booking booking = getBookingById(bookingId);

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Can only approve PENDING bookings");
        }

        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);
    }

    /**
     * Reject booking (for admin)
     */
    public void rejectBooking(Integer bookingId, String reason) {
        Booking booking = getBookingById(bookingId);

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Can only reject PENDING bookings");
        }

        booking.setStatus(BookingStatus.REJECTED);
        booking.setCancellationReason(reason);
        bookingRepository.save(booking);
    }

    /**
     * Complete booking (for admin)
     */
    public void completeBooking(Integer bookingId) {
        Booking booking = getBookingById(bookingId);

        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new RuntimeException("Can only complete CONFIRMED bookings");
        }

        booking.setStatus(BookingStatus.COMPLETED);
        bookingRepository.save(booking);
    }

    /**
     * Get bookings for a car
     */
    public List<Booking> getCarBookings(Integer carId) {
        return bookingRepository.findByCarId(carId);
    }
}
