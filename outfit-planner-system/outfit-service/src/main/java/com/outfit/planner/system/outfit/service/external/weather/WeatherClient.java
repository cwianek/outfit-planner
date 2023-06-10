package com.outfit.planner.system.outfit.service.external.weather;

import com.outfit.planner.system.outfit.service.external.weather.model.WeatherResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherClient {

    public WeatherResponse getWeather(String latitude, String longitude) {
        String URL = "https://api.open-meteo.com/v1/forecast?latitude={latitude}&longitude={longitude}&current_weather=true";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<WeatherResponse> response = restTemplate.getForEntity(URL, WeatherResponse.class, latitude, longitude);

        return response.getBody();
    }
}
