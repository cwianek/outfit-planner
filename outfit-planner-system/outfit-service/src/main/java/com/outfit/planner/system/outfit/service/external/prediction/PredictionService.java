package com.outfit.planner.system.outfit.service.external.prediction;

import com.outfit.planner.system.outfit.service.business.model.Geolocation;
import com.outfit.planner.system.outfit.service.dto.OutfitDto;

import java.util.List;

public interface PredictionService {
    void appendPredictions(List<OutfitDto> outfitDtos, Geolocation geolocation);
}
