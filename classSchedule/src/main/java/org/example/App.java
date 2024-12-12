package org.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 */


@SpringBootApplication

public class App extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return super.configure(builder) ;
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        System.out.println("Application started successfully!");
        System.out.println("Available endpoints:");
        System.out.println("- GET /schedule/addClass");
        System.out.println("- GET /schedule/classShow");
    }
}
