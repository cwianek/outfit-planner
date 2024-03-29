package com.outfit.planner.system.keycloak;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class KeycloakTestConfig {

    @Bean
    @Primary
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder) {
        String systemPropertyValue = System.getProperty("KEYCLOAK_URL");
        return webClientBuilder.baseUrl(systemPropertyValue).build();
    }

}
