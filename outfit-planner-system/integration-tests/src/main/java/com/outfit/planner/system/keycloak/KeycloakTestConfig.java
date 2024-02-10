package com.outfit.planner.system.keycloak;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Configuration
//@EnableAutoConfiguration
//@ComponentScan("com.outfit.planner.system.keycloak")
public class KeycloakTestConfig {

    @Bean
    @Primary
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder) {
        String systemPropertyValue = System.getProperty("KEYCLOAK_URL");
        // Configure WebClient with any necessary settings for your external service
        return webClientBuilder.baseUrl(systemPropertyValue).build();
    }

}
