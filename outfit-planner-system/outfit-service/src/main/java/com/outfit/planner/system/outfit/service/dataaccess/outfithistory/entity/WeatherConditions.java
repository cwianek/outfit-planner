package com.outfit.planner.system.outfit.service.dataaccess.outfithistory.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherConditions {
    private String temperature;
    private String windSpeed;
    private String weatherCode;
}
