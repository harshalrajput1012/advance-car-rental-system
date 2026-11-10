package com.driveease.controller;

import com.driveease.dto.ApiResponse;
import com.driveease.dto.CarResponse;
import com.driveease.entity.Car;
import com.driveease.entity.Car.FuelType;
import com.driveease.entity.Car.Transmission;
import com.driveease.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Car Controller
 * Handles car-related operations
 */
@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CarController {

    private final CarService carService;

    /**
     * Get all cars
     * GET /api/cars
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<CarResponse>>> getAllCars() {
        List<CarResponse> cars = carService.getAllCars();
        return ResponseEntity.ok(ApiResponse.success(cars));
    }

    /**
     * Get all available cars
     * GET /api/cars/available
     */
    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<CarResponse>>> getAvailableCars() {
        List<CarResponse> cars = carService.getAvailableCars();
        return ResponseEntity.ok(ApiResponse.success(cars));
    }

    /**
     * Get car by ID
     * GET /api/cars/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CarResponse>> getCarById(@PathVariable Integer id) {
        try {
            CarResponse car = carService.getCarById(id);
            return ResponseEntity.ok(ApiResponse.success(car));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * Search cars by keyword
     * GET /api/cars/search?keyword={keyword}
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<CarResponse>>> searchCars(@RequestParam String keyword) {
        List<CarResponse> cars = carService.searchCars(keyword);
        return ResponseEntity.ok(ApiResponse.success(cars));
    }

    /**
     * Filter cars by brand
     * GET /api/cars/filter/brand?brand={brand}
     */
    @GetMapping("/filter/brand")
    public ResponseEntity<ApiResponse<List<CarResponse>>> filterByBrand(@RequestParam String brand) {
        List<CarResponse> cars = carService.filterByBrand(brand);
        return ResponseEntity.ok(ApiResponse.success(cars));
    }

    /**
     * Filter cars by fuel type
     * GET /api/cars/filter/fuel-type?type={type}
     */
    @GetMapping("/filter/fuel-type")
    public ResponseEntity<ApiResponse<List<CarResponse>>> filterByFuelType(@RequestParam FuelType type) {
        List<CarResponse> cars = carService.filterByFuelType(type);
        return ResponseEntity.ok(ApiResponse.success(cars));
    }

    /**
     * Filter cars by transmission
     * GET /api/cars/filter/transmission?type={type}
     */
    @GetMapping("/filter/transmission")
    public ResponseEntity<ApiResponse<List<CarResponse>>> filterByTransmission(@RequestParam Transmission type) {
        List<CarResponse> cars = carService.filterByTransmission(type);
        return ResponseEntity.ok(ApiResponse.success(cars));
    }

    /**
     * Filter cars by price range
     * GET /api/cars/filter/price?min={min}&max={max}
     */
    @GetMapping("/filter/price")
    public ResponseEntity<ApiResponse<List<CarResponse>>> filterByPriceRange(
            @RequestParam Double min,
            @RequestParam Double max) {
        List<CarResponse> cars = carService.filterByPriceRange(min, max);
        return ResponseEntity.ok(ApiResponse.success(cars));
    }

    /**
     * Filter cars by seating capacity
     * GET /api/cars/filter/seating?capacity={capacity}
     */
    @GetMapping("/filter/seating")
    public ResponseEntity<ApiResponse<List<CarResponse>>> filterBySeatingCapacity(
            @RequestParam Integer capacity) {
        List<CarResponse> cars = carService.filterBySeatingCapacity(capacity);
        return ResponseEntity.ok(ApiResponse.success(cars));
    }

    /**
     * Filter cars by category
     * GET /api/cars/filter/category?categoryId={categoryId}
     */
    @GetMapping("/filter/category")
    public ResponseEntity<ApiResponse<List<CarResponse>>> filterByCategory(
            @RequestParam Integer categoryId) {
        List<CarResponse> cars = carService.filterByCategory(categoryId);
        return ResponseEntity.ok(ApiResponse.success(cars));
    }

    /**
     * Add new car (Admin only)
     * POST /api/cars
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Car>> addCar(@RequestBody Car car) {
        try {
            Car savedCar = carService.addCar(car);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(savedCar, "Car added successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * Update car (Admin only)
     * PUT /api/cars/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Car>> updateCar(
            @PathVariable Integer id,
            @RequestBody Car car) {
        try {
            Car updatedCar = carService.updateCar(id, car);
            return ResponseEntity.ok(ApiResponse.success(updatedCar, "Car updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * Delete car (Admin only)
     * DELETE /api/cars/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCar(@PathVariable Integer id) {
        try {
            carService.deleteCar(id);
            return ResponseEntity.ok(ApiResponse.success(null, "Car deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * Update car availability (Admin only)
     * PATCH /api/cars/{id}/availability
     */
    @PatchMapping("/{id}/availability")
    public ResponseEntity<ApiResponse<Void>> updateCarAvailability(
            @PathVariable Integer id,
            @RequestParam Boolean isAvailable) {
        try {
            carService.updateCarAvailability(id, isAvailable);
            return ResponseEntity.ok(ApiResponse.success(null, "Car availability updated"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}
