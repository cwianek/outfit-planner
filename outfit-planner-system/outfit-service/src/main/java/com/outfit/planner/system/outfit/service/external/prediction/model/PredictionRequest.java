package com.outfit.planner.system.outfit.service.external.prediction.model;

import com.outfit.planner.system.outfit.service.dataaccess.outfithistory.entity.OutfitHistoryEntity;
import com.outfit.planner.system.outfit.service.dataaccess.outfithistory.entity.WeatherConditions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PredictionRequest {
    private List<OutfitHistoryEntity> outfits;
    private WeatherConditions weatherConditions;
}
