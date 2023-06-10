package com.outfit.planner.system.outfit.service.external.weather.model;

import lombok.Data;

@Data
public class CurrentWeather {
    private String temperature;
    private String windspeed;
    private String weathercode;
}
