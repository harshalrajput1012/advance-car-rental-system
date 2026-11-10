-- ============================================
-- DriveEase Car Rental Management System
-- Database Creation Script
-- ============================================

-- Drop database if exists (for clean setup)
DROP DATABASE IF EXISTS driveease_db;

-- Create database
CREATE DATABASE driveease_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Use the database
USE driveease_db;

-- ============================================
-- TABLE: roles
-- Purpose: Store user roles (CUSTOMER, ADMIN)
-- ============================================
CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create index on role_name for faster lookups
CREATE INDEX idx_role_name ON roles(role_name);

-- ============================================
-- TABLE: users
-- Purpose: Store user information (customers and admins)
-- Relationships:
--   - role_id (FK) → roles.id
-- ============================================
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role_id INT NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    mobile_number VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,  -- Will store BCrypt hashed password
    address TEXT,
    city VARCHAR(100),
    state VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(100) DEFAULT 'India',
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Create indexes for faster queries
CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_user_role ON users(role_id);
CREATE INDEX idx_user_active ON users(is_active);

-- ============================================
-- TABLE: car_categories
-- Purpose: Store car categories (SUV, Sedan, Hatchback, etc.)
-- ============================================
CREATE TABLE car_categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create index on category_name
CREATE INDEX idx_category_name ON car_categories(category_name);

-- ============================================
-- TABLE: cars
-- Purpose: Store car details
-- Relationships:
--   - category_id (FK) → car_categories.id
-- ============================================
CREATE TABLE cars (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT NOT NULL,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(100) NOT NULL,
    year INT NOT NULL,
    color VARCHAR(50),
    license_plate VARCHAR(20) NOT NULL UNIQUE,
    fuel_type ENUM('PETROL', 'DIESEL', 'ELECTRIC', 'HYBRID', 'CNG') NOT NULL,
    transmission ENUM('MANUAL', 'AUTOMATIC') NOT NULL,
    seating_capacity INT NOT NULL,
    price_per_day DECIMAL(10, 2) NOT NULL,
    image_url VARCHAR(500),
    description TEXT,
    features JSON,  -- Store features as JSON (AC, GPS, Bluetooth, etc.)
    is_available BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES car_categories(id) ON DELETE CASCADE
);

-- Create indexes for faster queries
CREATE INDEX idx_car_brand ON cars(brand);
CREATE INDEX idx_car_category ON cars(category_id);
CREATE INDEX idx_car_fuel_type ON cars(fuel_type);
CREATE INDEX idx_car_transmission ON cars(transmission);
CREATE INDEX idx_car_price ON cars(price_per_day);
CREATE INDEX idx_car_available ON cars(is_available);
CREATE INDEX idx_car_license_plate ON cars(license_plate);

-- ============================================
-- TABLE: bookings
-- Purpose: Store booking information
-- Relationships:
--   - user_id (FK) → users.id
--   - car_id (FK) → cars.id
-- ============================================
CREATE TABLE bookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    car_id INT NOT NULL,
    pickup_location VARCHAR(255) NOT NULL,
    dropoff_location VARCHAR(255) NOT NULL,
    pickup_date DATETIME NOT NULL,
    return_date DATETIME NOT NULL,
    rental_days INT NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    status ENUM('PENDING', 'CONFIRMED', 'REJECTED', 'CANCELLED', 'COMPLETED') DEFAULT 'PENDING',
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    cancellation_reason TEXT,
    cancelled_at TIMESTAMP NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (car_id) REFERENCES cars(id) ON DELETE CASCADE,
    CONSTRAINT chk_dates CHECK (return_date > pickup_date),
    CONSTRAINT chk_rental_days CHECK (rental_days > 0)
);

-- Create indexes for faster queries
CREATE INDEX idx_booking_user ON bookings(user_id);
CREATE INDEX idx_booking_car ON bookings(car_id);
CREATE INDEX idx_booking_status ON bookings(status);
CREATE INDEX idx_booking_dates ON bookings(pickup_date, return_date);
CREATE INDEX idx_booking_date_range ON bookings(car_id, pickup_date, return_date);

-- ============================================
-- TABLE: payments
-- Purpose: Store payment information
-- Relationships:
--   - booking_id (FK) → bookings.id
-- ============================================
CREATE TABLE payments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT NOT NULL UNIQUE,
    amount DECIMAL(10, 2) NOT NULL,
    payment_method ENUM('CREDIT_CARD', 'DEBIT_CARD', 'UPI', 'NET_BANKING', 'CASH') NOT NULL,
    payment_status ENUM('PENDING', 'PAID', 'REFUNDED', 'FAILED') DEFAULT 'PENDING',
    transaction_id VARCHAR(100),
    payment_date TIMESTAMP NULL,
    refund_date TIMESTAMP NULL,
    refund_reason TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE CASCADE
);

-- Create indexes for faster queries
CREATE INDEX idx_payment_booking ON payments(booking_id);
CREATE INDEX idx_payment_status ON payments(payment_status);
CREATE INDEX idx_payment_date ON payments(payment_date);

-- ============================================
-- TABLE: reviews
-- Purpose: Store customer reviews for cars
-- Relationships:
--   - user_id (FK) → users.id
--   - car_id (FK) → cars.id
-- ============================================
CREATE TABLE reviews (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    car_id INT NOT NULL,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (car_id) REFERENCES cars(id) ON DELETE CASCADE,
    CONSTRAINT unique_user_car_review UNIQUE (user_id, car_id)
);

-- Create indexes for faster queries
CREATE INDEX idx_review_user ON reviews(user_id);
CREATE INDEX idx_review_car ON reviews(car_id);
CREATE INDEX idx_review_rating ON reviews(rating);

-- ============================================
-- END OF DATABASE CREATION SCRIPT
-- ============================================
