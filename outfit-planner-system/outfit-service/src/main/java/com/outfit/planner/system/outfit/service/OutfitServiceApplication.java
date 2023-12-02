package com.outfit.planner.system.outfit.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackages = "com.outfit.planner")
@SpringBootApplication(scanBasePackages = "com.outfit.planner.system")
public class OutfitServiceApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(OutfitServiceApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(OutfitServiceApplication.class, args);
    }
}
