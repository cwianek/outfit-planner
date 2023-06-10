package com.outfit.planner.system.outfit.service.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "prediction-service-config")
public class PredictionServiceConfigData {
    private String hostname;
    private String port;
}
