package com.outfit.planner.system.outfit.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class OutfitDto {
    private String id;
    private List<ProductDto> products;
    private Boolean wornToday;
    private Double matchProbability;
}
