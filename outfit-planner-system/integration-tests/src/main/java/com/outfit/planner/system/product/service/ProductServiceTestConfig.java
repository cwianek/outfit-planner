package com.outfit.planner.system.product.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ProductServiceTestConfig {

    @Bean
    @Primary
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder) {
        String systemPropertyValue = System.getProperty("PRODUCT_SERVICE_URL", "http://localhost:8184");
        return webClientBuilder.baseUrl(systemPropertyValue).build();
    }

}
