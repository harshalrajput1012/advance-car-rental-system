package com.driveease.service;

import com.driveease.dto.CarResponse;
import com.driveease.entity.Car;
import com.driveease.entity.Car.FuelType;
import com.driveease.entity.Car.Transmission;
import com.driveease.entity.CarCategory;
import com.driveease.exception.ResourceNotFoundException;
import com.driveease.repository.CarCategoryRepository;
import com.driveease.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for Car operations
 * Contains business logic related to car management
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CarService {

    private final CarRepository carRepository;
    private final CarCategoryRepository carCategoryRepository;

    /**
     * Get all cars
     */
    public List<CarResponse> getAllCars() {
        List<Car> cars = carRepository.findAll();
        return cars.stream()
                .map(CarResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Get all available cars
     */
    public List<CarResponse> getAvailableCars() {
        List<Car> cars = carRepository.findByIsAvailableTrue();
        return cars.stream()
                .map(CarResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Get car by ID
     */
    public CarResponse getCarById(Integer id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car", id));
        return CarResponse.fromEntity(car);
    }

    /**
     * Search cars by keyword (brand or model)
     */
    public List<CarResponse> searchCars(String keyword) {
        List<Car> cars = carRepository.searchCars(keyword);
        return cars.stream()
                .map(CarResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Filter cars by brand
     */
    public List<CarResponse> filterByBrand(String brand) {
        List<Car> cars = carRepository.findByBrand(brand);
        return cars.stream()
                .map(CarResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Filter cars by fuel type
     */
    public List<CarResponse> filterByFuelType(FuelType fuelType) {
        List<Car> cars = carRepository.findByFuelType(fuelType);
        return cars.stream()
                .map(CarResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Filter cars by transmission
     */
    public List<CarResponse> filterByTransmission(Transmission transmission) {
        List<Car> cars = carRepository.findByTransmission(transmission);
        return cars.stream()
                .map(CarResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Filter cars by price range
     */
    public List<CarResponse> filterByPriceRange(Double minPrice, Double maxPrice) {
        List<Car> cars = carRepository.findByPricePerDayBetween(minPrice, maxPrice);
        return cars.stream()
                .map(CarResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Filter cars by seating capacity
     */
    public List<CarResponse> filterBySeatingCapacity(Integer seatingCapacity) {
        List<Car> cars = carRepository.findBySeatingCapacityGreaterThanEqual(seatingCapacity);
        return cars.stream()
                .map(CarResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Filter cars by category
     */
    public List<CarResponse> filterByCategory(Integer categoryId) {
        List<Car> cars = carRepository.findByCategoryId(categoryId);
        return cars.stream()
                .map(CarResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Add new car (for admin)
     */
    public Car addCar(Car car) {
        // Validate category exists
        CarCategory category = carCategoryRepository.findById(car.getCategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException("CarCategory", car.getCategory().getId()));
        car.setCategory(category);

        return carRepository.save(car);
    }

    /**
     * Update car (for admin)
     */
    public Car updateCar(Integer id, Car carDetails) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car", id));

        car.setBrand(carDetails.getBrand());
        car.setModel(carDetails.getModel());
        car.setYear(carDetails.getYear());
        car.setColor(carDetails.getColor());
        car.setLicensePlate(carDetails.getLicensePlate());
        car.setFuelType(carDetails.getFuelType());
        car.setTransmission(carDetails.getTransmission());
        car.setSeatingCapacity(carDetails.getSeatingCapacity());
        car.setPricePerDay(carDetails.getPricePerDay());
        car.setImageUrl(carDetails.getImageUrl());
        car.setDescription(carDetails.getDescription());
        car.setFeatures(carDetails.getFeatures());
        car.setIsAvailable(carDetails.getIsAvailable());

        if (carDetails.getCategory() != null) {
            CarCategory category = carCategoryRepository.findById(carDetails.getCategory().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("CarCategory", carDetails.getCategory().getId()));
            car.setCategory(category);
        }

        return carRepository.save(car);
    }

    /**
     * Delete car (for admin)
     */
    public void deleteCar(Integer id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car", id));
        carRepository.delete(car);
    }

    /**
     * Update car availability
     */
    public void updateCarAvailability(Integer id, Boolean isAvailable) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car", id));
        car.setIsAvailable(isAvailable);
        carRepository.save(car);
    }
}
