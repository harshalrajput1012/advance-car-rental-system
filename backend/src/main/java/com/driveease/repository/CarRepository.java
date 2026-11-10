package com.driveease.repository;

import com.driveease.entity.Car;
import com.driveease.entity.Car.FuelType;
import com.driveease.entity.Car.Transmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Car entity
 * Provides CRUD operations and custom queries for Car
 */
@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    /**
     * Find all available cars
     * @return List of available cars
     */
    List<Car> findByIsAvailableTrue();

    /**
     * Find cars by brand
     * @param brand Car brand
     * @return List of cars with the specified brand
     */
    List<Car> findByBrand(String brand);

    /**
     * Find cars by fuel type
     * @param fuelType Fuel type (PETROL, DIESEL, etc.)
     * @return List of cars with the specified fuel type
     */
    List<Car> findByFuelType(FuelType fuelType);

    /**
     * Find cars by transmission
     * @param transmission Transmission type (MANUAL, AUTOMATIC)
     * @return List of cars with the specified transmission
     */
    List<Car> findByTransmission(Transmission transmission);

    /**
     * Find cars by price range
     * @param minPrice Minimum price per day
     * @param maxPrice Maximum price per day
     * @return List of cars within the price range
     */
    List<Car> findByPricePerDayBetween(Double minPrice, Double maxPrice);

    /**
     * Find cars by seating capacity
     * @param seatingCapacity Required seating capacity
     * @return List of cars with at least the specified seating capacity
     */
    List<Car> findBySeatingCapacityGreaterThanEqual(Integer seatingCapacity);

    /**
     * Search cars by brand or model name
     * @param keyword Search keyword
     * @return List of cars matching the keyword
     */
    @Query("SELECT c FROM Car c WHERE c.brand LIKE %:keyword% OR c.model LIKE %:keyword%")
    List<Car> searchCars(@Param("keyword") String keyword);

    /**
     * Find cars by category
     * @param categoryId Category ID
     * @return List of cars in the specified category
     */
    List<Car> findByCategoryId(Integer categoryId);
}
