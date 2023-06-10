package com.outfit.planner.system.outfit.service.mapper;

import com.outfit.planner.system.outfit.service.dataaccess.outfithistory.entity.WeatherConditions;
import com.outfit.planner.system.outfit.service.external.weather.model.WeatherResponse;
import org.springframework.stereotype.Component;

@Component
public class WeatherMapper {

    public WeatherConditions weatherResponseToWeatherConditionsMapper(WeatherResponse weatherResponse){
        return WeatherConditions.builder()
                .weatherCode(weatherResponse.getCurrentWeather().getWeathercode())
                .windSpeed(weatherResponse.getCurrentWeather().getWindspeed())
                .temperature(weatherResponse.getCurrentWeather().getTemperature())
                .build();
    }

}
