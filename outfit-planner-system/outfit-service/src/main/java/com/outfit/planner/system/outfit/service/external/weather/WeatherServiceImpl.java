package com.outfit.planner.system.outfit.service.external.weather;

import com.outfit.planner.system.outfit.service.business.model.Geolocation;
import com.outfit.planner.system.outfit.service.business.model.ToggleWearOutfitRequest;
import com.outfit.planner.system.outfit.service.dataaccess.outfithistory.entity.WeatherConditions;
import com.outfit.planner.system.outfit.service.external.weather.model.WeatherResponse;
import com.outfit.planner.system.outfit.service.mapper.WeatherMapper;
import org.springframework.stereotype.Service;

@Service
class WeatherServiceImpl implements WeatherService {

    private final WeatherClient weatherClient;
    private final WeatherMapper weatherMapper;

    WeatherServiceImpl(WeatherClient weatherClient, WeatherMapper weatherMapper) {
        this.weatherClient = weatherClient;
        this.weatherMapper = weatherMapper;
    }

    @Override
    public WeatherConditions getWeather(Geolocation geolocation) {
        WeatherResponse weather = weatherClient.getWeather(geolocation.getLatitude(), geolocation.getLongitude());
        return weatherMapper.weatherResponseToWeatherConditionsMapper(weather);
    }
}
