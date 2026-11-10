package com.driveease;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application class for DriveEase
 * This is the entry point of the application
 * 
 * @SpringBootApplication annotation combines:
 * - @Configuration: Marks the class as a source of bean definitions
 * - @EnableAutoConfiguration: Enables Spring Boot's auto-configuration
 * - @ComponentScan: Scans for components in the current package and sub-packages
 */
@SpringBootApplication
public class DriveEaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(DriveEaseApplication.class, args);
        System.out.println("========================================");
        System.out.println("DriveEase Application Started Successfully!");
        System.out.println("Access API at: http://localhost:8080/api");
        System.out.println("========================================");
    }
}
