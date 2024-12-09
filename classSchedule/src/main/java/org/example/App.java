package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 */



@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        System.out.println("Application started successfully!");
        System.out.println("Available endpoints:");
        System.out.println("- GET /schedule/addClass");
        System.out.println("- GET /schedule/classShow");
    }
}
