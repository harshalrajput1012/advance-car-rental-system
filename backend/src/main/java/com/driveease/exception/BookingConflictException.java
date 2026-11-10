package com.driveease.exception;

/**
 * Custom exception thrown when there's a booking conflict
 * Used when trying to book a car that's already booked for overlapping dates
 */
public class BookingConflictException extends RuntimeException {

    public BookingConflictException(String message) {
        super(message);
    }

    public BookingConflictException() {
        super("The car is already booked for the requested dates. Please choose different dates.");
    }
}
