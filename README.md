# DriveEase - Car Rental Management System

## Project Overview
DriveEase is a professional, industry-level Car Rental Management System built for MCA academic project and software developer interviews.

## Technology Stack

### Frontend
- HTML5
- CSS3
- JavaScript
- Bootstrap 5

### Backend
- Java 17+
- Spring Boot 3.x
- Spring MVC
- Spring Data JPA
- Hibernate
- REST APIs

### Database
- MySQL 8.0+
- MySQL Workbench

### Development Tools
- IntelliJ IDEA / VS Code
- Maven
- Postman
- Git
- GitHub

## Project Structure

```
DriveEase/
├── backend/          # Spring Boot application
├── frontend/         # HTML, CSS, JavaScript files
├── database/         # SQL scripts
├── screenshots/      # Project screenshots
└── README.md         # This file
```

## Features

### Customer Module
- User Registration & Login
- Car Browsing & Search
- Car Details View
- Booking System
- My Bookings
- Profile Management

### Admin Module
- Admin Dashboard
- Car Management (CRUD)
- Customer Management
- Booking Management
- Revenue Reports

## Installation Instructions

### Prerequisites
1. Java JDK 17 or higher
2. Maven 3.8+
3. MySQL 8.0+
4. IntelliJ IDEA or VS Code

### Setup Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/DriveEase.git
   cd DriveEase
   ```

2. **Database Setup**
   - Open MySQL Workbench
   - Execute `database/driveease_db.sql`
   - Execute `database/sample_data.sql`

3. **Backend Configuration**
   - Open `backend/src/main/resources/application.properties`
   - Update MySQL username and password
   - Run the application:
     ```bash
     cd backend
     mvn spring-boot:run
     ```
   - Backend will run on `http://localhost:8080`

4. **Frontend Setup**
   - Open `frontend/index.html` in browser
   - Or use Live Server extension in VS Code

## API Endpoints

### Authentication
- POST `/api/auth/register` - User registration
- POST `/api/auth/login` - User login

### Cars
- GET `/api/cars` - Get all cars
- GET `/api/cars/{id}` - Get car by ID
- GET `/api/cars/search` - Search cars

### Bookings
- POST `/api/bookings` - Create booking
- GET `/api/bookings/user/{userId}` - Get user bookings
- PUT `/api/bookings/{id}/cancel` - Cancel booking

### Admin
- GET `/api/admin/dashboard` - Dashboard statistics
- POST `/api/admin/cars` - Add car
- PUT `/api/admin/cars/{id}` - Update car
- DELETE `/api/admin/cars/{id}` - Delete car

## Database Schema

### Tables
- users
- roles
- cars
- car_categories
- bookings
- payments
- reviews

## Business Rules

1. Pickup date must be before return date
2. No overlapping bookings for same car
3. Automatic rental cost calculation
4. Role-based authorization (Customer/Admin)
5. Booking statuses: PENDING, CONFIRMED, REJECTED, CANCELLED, COMPLETED
6. Payment statuses: PENDING, PAID, REFUNDED

## Security Features

- BCrypt password hashing
- Role-based authorization
- Input validation
- Exception handling
- Secure database queries

## Running the Application

### Backend
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

### Frontend
- Open `frontend/index.html` in browser
- Or use VS Code Live Server

## Testing APIs with Postman

1. Import Postman collection (if available)
2. Set base URL: `http://localhost:8080/api`
3. Test each endpoint

## Project Screenshots

### Customer Module
- Home Page with hero section and featured cars
- Car Listing with search and filters
- Car Details page
- Booking form with automatic price calculation
- My Bookings page with booking status
- Profile management page

### Admin Module
- Admin Dashboard with statistics
- Car Management (Add/Edit/Delete)
- Booking Management (Approve/Reject/Complete)
- Customer Management

## Default Credentials

### Admin Account
- Email: admin@driveease.com
- Password: admin123

### Customer Account (Sample)
- Email: rahul.sharma@email.com
- Password: customer123

## Project Statistics

- **Total Lines of Code:** ~5,000+
- **Backend Classes:** 30+
- **Frontend Pages:** 12
- **Database Tables:** 7
- **API Endpoints:** 25+
- **Development Time:** ~40 hours

## Future Enhancements

- JWT Token-based authentication
- Real-time car availability calendar
- Payment gateway integration (Razorpay/Stripe)
- Email notifications for booking confirmations
- Car image upload functionality
- Advanced analytics and reporting
- Mobile app (React Native/Flutter)
- Multi-language support
- Rating and review system
- Loyalty program for customers

## Troubleshooting

### Common Issues

**Issue: Backend won't start**
- Solution: Check MySQL service is running, verify database credentials in application.properties

**Issue: CORS errors in browser**
- Solution: Ensure backend is running, check CorsConfig.java configuration

**Issue: Database connection failed**
- Solution: Verify MySQL password, check database name is driveease_db

**Issue: Frontend not loading data**
- Solution: Check browser console for errors, verify API_BASE_URL in js/main.js

## Interview Preparation

### Key Concepts Covered
- Spring Boot Architecture
- REST API Design
- JPA/Hibernate ORM
- MVC Pattern
- Database Design
- Security Implementation
- Frontend-Backend Integration

### Common Interview Questions

See `interview-preparation.md` for 50+ project-based interview questions with answers.

## License

This project is for educational purposes.

## Author

[Your Name]
MCA Student

## Acknowledgments

- Spring Boot Documentation
- MySQL Documentation
- Bootstrap Documentation
