package com.outfit.planner.system.outfit.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackages = "com.outfit.planner")
@SpringBootApplication(scanBasePackages = "com.outfit.planner.system")
public class OutfitServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OutfitServiceApplication.class, args);
    }
}
