package com.outfit.planner.system.outfit.service.external.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeatherResponse {
    private String latitude;
    private String longitude;
    @JsonProperty("current_weather")
    private CurrentWeather currentWeather;
}
