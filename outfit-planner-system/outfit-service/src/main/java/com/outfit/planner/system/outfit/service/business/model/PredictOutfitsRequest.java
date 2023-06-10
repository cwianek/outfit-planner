package com.outfit.planner.system.outfit.service.business.model;

import lombok.Data;

@Data
public class PredictOutfitsRequest {
    private Geolocation geolocation;
    private String username;
}
