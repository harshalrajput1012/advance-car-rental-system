package com.driveease.controller;

import com.driveease.dto.ApiResponse;
import com.driveease.dto.BookingRequest;
import com.driveease.entity.Booking;
import com.driveease.entity.Booking.BookingStatus;
import com.driveease.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Booking Controller
 * Handles booking-related operations
 */
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingService bookingService;

    /**
     * Create a new booking
     * POST /api/bookings
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Booking>> createBooking(@Valid @RequestBody BookingRequest request) {
        try {
            Booking booking = bookingService.createBooking(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(booking, "Booking created successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * Get booking by ID
     * GET /api/bookings/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Booking>> getBookingById(@PathVariable Integer id) {
        try {
            Booking booking = bookingService.getBookingById(id);
            return ResponseEntity.ok(ApiResponse.success(booking));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * Get all bookings for a user
     * GET /api/bookings/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Booking>>> getUserBookings(@PathVariable Integer userId) {
        List<Booking> bookings = bookingService.getUserBookings(userId);
        return ResponseEntity.ok(ApiResponse.success(bookings));
    }

    /**
     * Get active bookings for a user
     * GET /api/bookings/user/{userId}/active
     */
    @GetMapping("/user/{userId}/active")
    public ResponseEntity<ApiResponse<List<Booking>>> getActiveBookings(@PathVariable Integer userId) {
        List<Booking> bookings = bookingService.getActiveBookings(userId);
        return ResponseEntity.ok(ApiResponse.success(bookings));
    }

    /**
     * Cancel booking
     * PUT /api/bookings/{id}/cancel
     */
    @PutMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancelBooking(
            @PathVariable Integer id,
            @RequestBody Map<String, String> request) {
        try {
            String reason = request.get("reason");
            bookingService.cancelBooking(id, reason);
            return ResponseEntity.ok(ApiResponse.success(null, "Booking cancelled successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * Approve booking (Admin only)
     * PUT /api/bookings/{id}/approve
     */
    @PutMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<Void>> approveBooking(@PathVariable Integer id) {
        try {
            bookingService.approveBooking(id);
            return ResponseEntity.ok(ApiResponse.success(null, "Booking approved successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * Reject booking (Admin only)
     * PUT /api/bookings/{id}/reject
     */
    @PutMapping("/{id}/reject")
    public ResponseEntity<ApiResponse<Void>> rejectBooking(
            @PathVariable Integer id,
            @RequestBody Map<String, String> request) {
        try {
            String reason = request.get("reason");
            bookingService.rejectBooking(id, reason);
            return ResponseEntity.ok(ApiResponse.success(null, "Booking rejected successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * Complete booking (Admin only)
     * PUT /api/bookings/{id}/complete
     */
    @PutMapping("/{id}/complete")
    public ResponseEntity<ApiResponse<Void>> completeBooking(@PathVariable Integer id) {
        try {
            bookingService.completeBooking(id);
            return ResponseEntity.ok(ApiResponse.success(null, "Booking completed successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * Get bookings by status (Admin only)
     * GET /api/bookings/status/{status}
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<Booking>>> getBookingsByStatus(
            @PathVariable BookingStatus status) {
        List<Booking> bookings = bookingService.getBookingsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(bookings));
    }
}
