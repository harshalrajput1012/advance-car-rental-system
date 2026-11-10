package com.driveease.controller;

import com.driveease.dto.ApiResponse;
import com.driveease.entity.Booking;
import com.driveease.entity.Booking.BookingStatus;
import com.driveease.service.BookingService;
import com.driveease.service.CarService;
import com.driveease.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Admin Controller
 * Handles admin-specific operations and dashboard statistics
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController {

    private final UserService userService;
    private final CarService carService;
    private final BookingService bookingService;

    /**
     * Get dashboard statistics
     * GET /api/admin/dashboard
     */
    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        // Total cars
        stats.put("totalCars", carService.getAllCars().size());
        
        // Total users
        stats.put("totalUsers", userService.getAllUsers().size());
        
        // Total bookings
        stats.put("totalBookings", bookingService.getAllBookings().size());
        
        // Active rentals (CONFIRMED bookings)
        stats.put("activeRentals", bookingService.getBookingsByStatus(BookingStatus.CONFIRMED).size());
        
        // Pending bookings
        stats.put("pendingBookings", bookingService.getBookingsByStatus(BookingStatus.PENDING).size());
        
        // Total revenue (sum of all CONFIRMED and COMPLETED bookings)
        List<Booking> completedBookings = bookingService.getBookingsByStatus(BookingStatus.COMPLETED);
        List<Booking> confirmedBookings = bookingService.getBookingsByStatus(BookingStatus.CONFIRMED);
        
        double totalRevenue = completedBookings.stream()
                .mapToDouble(Booking::getTotalAmount)
                .sum();
        totalRevenue += confirmedBookings.stream()
                .mapToDouble(Booking::getTotalAmount)
                .sum();
        
        stats.put("totalRevenue", totalRevenue);
        
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    /**
     * Get recent bookings
     * GET /api/admin/bookings/recent
     */
    @GetMapping("/bookings/recent")
    public ResponseEntity<ApiResponse<List<Booking>>> getRecentBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        // Return last 10 bookings
        int fromIndex = Math.max(0, bookings.size() - 10);
        List<Booking> recentBookings = bookings.subList(fromIndex, bookings.size());
        return ResponseEntity.ok(ApiResponse.success(recentBookings));
    }

    /**
     * Get all bookings
     * GET /api/admin/bookings
     */
    @GetMapping("/bookings")
    public ResponseEntity<ApiResponse<List<Booking>>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(ApiResponse.success(bookings));
    }

    /**
     * Get bookings by status
     * GET /api/admin/bookings/status/{status}
     */
    @GetMapping("/bookings/status/{status}")
    public ResponseEntity<ApiResponse<List<Booking>>> getBookingsByStatus(
            @PathVariable BookingStatus status) {
        List<Booking> bookings = bookingService.getBookingsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(bookings));
    }
}
