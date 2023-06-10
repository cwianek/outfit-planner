package com.outfit.planner.system.outfit.service.business.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ToggleWearOutfitRequest {
    private Geolocation geolocation;
    private String id;
    private LocalDate wearDate;
}
