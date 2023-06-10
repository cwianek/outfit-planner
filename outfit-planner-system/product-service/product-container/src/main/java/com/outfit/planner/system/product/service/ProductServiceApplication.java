package com.outfit.planner.system.product.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories(basePackages = { "com.outfit.planner.system.product.service.dataaccess"})
@EntityScan(basePackages = { "com.outfit.planner.system.product.service.dataaccess" })
@SpringBootApplication(scanBasePackages = "com.outfit.planner.system")
public class ProductServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
}
