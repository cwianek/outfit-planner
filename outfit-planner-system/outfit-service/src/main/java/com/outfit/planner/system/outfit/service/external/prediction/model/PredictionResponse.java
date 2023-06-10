package com.outfit.planner.system.outfit.service.external.prediction.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PredictionResponse {
    private Map<String, Double> predictions;
}
