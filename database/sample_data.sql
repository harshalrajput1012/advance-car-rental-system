-- ============================================
-- DriveEase Car Rental Management System
-- Sample Data Insertion Script
-- ============================================

USE driveease_db;

-- ============================================
-- INSERT INTO roles
-- ============================================
INSERT INTO roles (role_name, description) VALUES
('ADMIN', 'Administrator with full system access'),
('CUSTOMER', 'Regular customer who can book cars');

-- ============================================
-- INSERT INTO car_categories
-- ============================================
INSERT INTO car_categories (category_name, description) VALUES
('SUV', 'Sport Utility Vehicle - spacious and powerful'),
('SEDAN', 'Sedan - comfortable family car'),
('HATCHBACK', 'Hatchback - compact and fuel-efficient'),
('LUXURY', 'Luxury - premium vehicles for special occasions'),
('MPV', 'Multi-Purpose Vehicle - large seating capacity'),
('CONVERTIBLE', 'Convertible - open-top driving experience'),
('ELECTRIC', 'Electric - eco-friendly electric vehicles');

-- ============================================
-- INSERT INTO users
-- Note: Passwords are BCrypt hashed
-- For testing:
-- - Admin password: admin123
-- - Customer password: customer123
-- ============================================
INSERT INTO users (role_id, full_name, email, mobile_number, password, address, city, state, postal_code, country, is_active) VALUES
-- Admin user (password: admin123)
(1, 'System Administrator', 'admin@driveease.com', '9876543210', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Admin Office', 'Mumbai', 'Maharashtra', '400001', 'India', TRUE),
-- Customer users (password: customer123)
(2, 'Rahul Sharma', 'rahul.sharma@email.com', '9876543211', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '123, Park Street', 'Delhi', 'Delhi', '110001', 'India', TRUE),
(2, 'Priya Patel', 'priya.patel@email.com', '9876543212', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '456, Garden Road', 'Mumbai', 'Maharashtra', '400002', 'India', TRUE),
(2, 'Amit Kumar', 'amit.kumar@email.com', '9876543213', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '789, Lake View', 'Bangalore', 'Karnataka', '560001', 'India', TRUE),
(2, 'Sneha Reddy', 'sneha.reddy@email.com', '9876543214', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '321, Hill Street', 'Hyderabad', 'Telangana', '500001', 'India', TRUE),
(2, 'Vikram Singh', 'vikram.singh@email.com', '9876543215', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '654, Ocean Drive', 'Chennai', 'Tamil Nadu', '600001', 'India', TRUE);

-- ============================================
-- INSERT INTO cars
-- ============================================
INSERT INTO cars (category_id, brand, model, year, color, license_plate, fuel_type, transmission, seating_capacity, price_per_day, image_url, description, features, is_available) VALUES
-- SUVs
(1, 'Toyota', 'Fortuner', 2023, 'White', 'MH01AB1234', 'DIESEL', 'AUTOMATIC', 7, 3500.00, 'https://example.com/images/toyota-fortuner.jpg', 'Powerful SUV with excellent off-road capabilities', '["AC", "GPS", "Bluetooth", "Airbags", "ABS", "Power Steering", "Cruise Control"]', TRUE),
(1, 'Mahindra', 'Scorpio', 2023, 'Black', 'MH01CD5678', 'DIESEL', 'MANUAL', 7, 2800.00, 'https://example.com/images/mahindra-scorpio.jpg', 'Rugged SUV perfect for Indian roads', '["AC", "GPS", "Bluetooth", "Airbags", "ABS", "Power Steering"]', TRUE),
(1, 'Hyundai', 'Creta', 2024, 'Silver', 'DL02EF9012', 'PETROL', 'AUTOMATIC', 5, 2200.00, 'https://example.com/images/hyundai-creta.jpg', 'Compact SUV with modern features', '["AC", "GPS", "Bluetooth", "Airbags", "ABS", "Touchscreen"]', TRUE),

-- Sedans
(2, 'Honda', 'City', 2024, 'Red', 'MH03GH3456', 'PETROL', 'AUTOMATIC', 5, 1800.00, 'https://example.com/images/honda-city.jpg', 'Premium sedan with excellent comfort', '["AC", "GPS", "Bluetooth", "Airbags", "ABS", "Sunroof"]', TRUE),
(2, 'Maruti Suzuki', 'Dzire', 2023, 'Blue', 'DL04IJ7890', 'PETROL', 'MANUAL', 5, 1500.00, 'https://example.com/images/maruti-dzire.jpg', 'Reliable sedan for city driving', '["AC", "Bluetooth", "Airbags", "ABS"]', TRUE),
(2, 'Skoda', 'Slavia', 2024, 'White', 'KA05KL2345', 'PETROL', 'AUTOMATIC', 5, 2000.00, 'https://example.com/images/skoda-slavia.jpg', 'Stylish sedan with premium features', '["AC", "GPS", "Bluetooth", "Airbags", "ABS", "Sunroof"]', TRUE),

-- Hatchbacks
(3, 'Maruti Suzuki', 'Swift', 2024, 'Grey', 'MH06MN6789', 'PETROL', 'MANUAL', 5, 1200.00, 'https://example.com/images/maruti-swift.jpg', 'Popular hatchback with great mileage', '["AC", "Bluetooth", "Airbags", "ABS"]', TRUE),
(3, 'Hyundai', 'i20', 2023, 'Orange', 'DL07OP0123', 'PETROL', 'AUTOMATIC', 5, 1400.00, 'https://example.com/images/hyundai-i20.jpg', 'Feature-rich hatchback', '["AC", "GPS", "Bluetooth", "Airbags", "ABS", "Touchscreen"]', TRUE),
(3, 'Tata', 'Altroz', 2024, 'Green', 'KA08QR4567', 'PETROL', 'MANUAL', 5, 1300.00, 'https://example.com/images/tata-altroz.jpg', 'Safe and stylish hatchback', '["AC", "Bluetooth", "Airbags", "ABS"]', TRUE),

-- Luxury Cars
(4, 'BMW', '5 Series', 2024, 'Black', 'MH09ST8901', 'DIESEL', 'AUTOMATIC', 5, 8000.00, 'https://example.com/images/bmw-5-series.jpg', 'Luxury sedan with premium comfort', '["AC", "GPS", "Bluetooth", "Airbags", "ABS", "Sunroof", "Leather Seats", "Heated Seats"]', TRUE),
(4, 'Mercedes-Benz', 'E-Class', 2024, 'Silver', 'DL10UV2345', 'DIESEL', 'AUTOMATIC', 5, 9000.00, 'https://example.com/images/mercedes-e-class.jpg', 'Ultimate luxury experience', '["AC", "GPS", "Bluetooth", "Airbags", "ABS", "Sunroof", "Leather Seats", "Massage Seats"]', TRUE),
(4, 'Audi', 'A6', 2023, 'White', 'KA11WX6789', 'DIESEL', 'AUTOMATIC', 5, 8500.00, 'https://example.com/images/audi-a6.jpg', 'German engineering at its best', '["AC", "GPS", "Bluetooth", "Airbags", "ABS", "Sunroof", "Leather Seats"]', TRUE),

-- MPV
(5, 'Toyota', 'Innova Crysta', 2024, 'White', 'MH12YZ0123', 'DIESEL', 'AUTOMATIC', 7, 4000.00, 'https://example.com/images/toyota-innova.jpg', 'Premium MPV for large families', '["AC", "GPS", "Bluetooth", "Airbags", "ABS", "Rear AC", "Captain Seats"]', TRUE),
(5, 'Maruti Suzuki', 'Ertiga', 2023, 'Grey', 'DL13AB4567', 'PETROL', 'MANUAL', 7, 2500.00, 'https://example.com/images/maruti-ertiga.jpg', 'Value for money MPV', '["AC", "Bluetooth", "Airbags", "ABS", "Rear AC"]', TRUE),

-- Convertible
(6, 'BMW', 'Z4', 2023, 'Red', 'KA14CD8901', 'PETROL', 'AUTOMATIC', 2, 12000.00, 'https://example.com/images/bmw-z4.jpg', 'Sporty convertible for thrill seekers', '["AC", "GPS", "Bluetooth", "Airbags", "ABS", "Leather Seats", "Soft Top"]', TRUE),

-- Electric
(7, 'Tata', 'Nexon EV', 2024, 'Blue', 'MH15EF2345', 'ELECTRIC', 'AUTOMATIC', 5, 2500.00, 'https://example.com/images/tata-nexon-ev.jpg', 'Electric SUV with great range', '["AC", "GPS", "Bluetooth", "Airbags", "ABS", "Touchscreen", "Regenerative Braking"]', TRUE),
(7, 'MG', 'ZS EV', 2023, 'White', 'DL16GH6789', 'ELECTRIC', 'AUTOMATIC', 5, 3000.00, 'https://example.com/images/mg-zs-ev.jpg', 'Premium electric SUV', '["AC", "GPS", "Bluetooth", "Airbags", "ABS", "Sunroof", "Touchscreen"]', TRUE);

-- ============================================
-- INSERT INTO bookings
-- ============================================
INSERT INTO bookings (user_id, car_id, pickup_location, dropoff_location, pickup_date, return_date, rental_days, total_amount, status) VALUES
-- Confirmed booking
(2, 1, 'Mumbai Airport', 'Pune Airport', '2024-12-01 10:00:00', '2024-12-05 10:00:00', 4, 14000.00, 'CONFIRMED'),
-- Pending booking
(3, 4, 'Delhi Airport', 'Agra', '2024-12-10 09:00:00', '2024-12-12 09:00:00', 2, 3600.00, 'PENDING'),
-- Completed booking
(4, 7, 'Bangalore City', 'Mysore', '2024-11-20 08:00:00', '2024-11-22 08:00:00', 2, 2400.00, 'COMPLETED'),
-- Cancelled booking
(5, 10, 'Hyderabad', 'Vijayawada', '2024-11-25 10:00:00', '2024-11-27 10:00:00', 2, 2800.00, 'CANCELLED');

-- ============================================
-- INSERT INTO payments
-- ============================================
INSERT INTO payments (booking_id, amount, payment_method, payment_status, transaction_id, payment_date) VALUES
-- Payment for confirmed booking
(1, 14000.00, 'CREDIT_CARD', 'PAID', 'TXN001234567890', '2024-12-01 10:30:00'),
-- Payment for pending booking (not paid yet)
(2, 3600.00, 'UPI', 'PENDING', NULL, NULL),
-- Payment for completed booking
(3, 2400.00, 'DEBIT_CARD', 'PAID', 'TXN009876543210', '2024-11-20 08:30:00'),
-- Refund for cancelled booking
(4, 2800.00, 'CREDIT_CARD', 'REFUNDED', 'TXN005555555555', '2024-11-25 10:30:00');

-- Update refund date for cancelled booking
UPDATE payments SET refund_date = '2024-11-26 14:00:00', refund_reason = 'Booking cancelled by customer' WHERE id = 4;

-- ============================================
-- INSERT INTO reviews
-- ============================================
INSERT INTO reviews (user_id, car_id, rating, comment) VALUES
(2, 1, 5, 'Excellent car! Very comfortable and powerful. The pickup was smooth and the car was in perfect condition.'),
(4, 7, 4, 'Good car, well-maintained. The AC was working perfectly. Would recommend for city trips.'),
(3, 4, 5, 'Amazing experience! The car was clean and the staff was very helpful. Will definitely book again.'),
(5, 10, 3, 'Average experience. The car was okay but had some minor issues. Overall acceptable.'),
(2, 7, 4, 'Great value for money. The car was fuel-efficient and comfortable for long drives.');

-- ============================================
-- END OF SAMPLE DATA SCRIPT
-- ============================================

-- Verification queries to check data
SELECT 'Roles count:' AS info, COUNT(*) AS count FROM roles;
SELECT 'Users count:' AS info, COUNT(*) AS count FROM users;
SELECT 'Car Categories count:' AS info, COUNT(*) AS count FROM car_categories;
SELECT 'Cars count:' AS info, COUNT(*) AS count FROM cars;
SELECT 'Bookings count:' AS info, COUNT(*) AS count FROM bookings;
SELECT 'Payments count:' AS info, COUNT(*) AS count FROM payments;
SELECT 'Reviews count:' AS info, COUNT(*) AS count FROM reviews;
