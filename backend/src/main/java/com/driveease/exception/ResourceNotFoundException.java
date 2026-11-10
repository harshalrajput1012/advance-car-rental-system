package com.driveease.exception;

/**
 * Custom exception thrown when a requested resource is not found
 * Used when trying to access a user, car, booking, etc. that doesn't exist
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, Integer id) {
        super(String.format("%s not found with id: %d", resourceName, id));
    }

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue));
    }
}
