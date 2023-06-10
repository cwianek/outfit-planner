package com.outfit.planner.system.outfit.service.external.weather;

import com.outfit.planner.system.outfit.service.business.model.Geolocation;
import com.outfit.planner.system.outfit.service.business.model.ToggleWearOutfitRequest;
import com.outfit.planner.system.outfit.service.dataaccess.outfithistory.entity.WeatherConditions;

public interface WeatherService {
    WeatherConditions getWeather(Geolocation geolocation);
}
