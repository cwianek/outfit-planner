package com.outfit.planner.system.outfit.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {
    private String id;
    private String category;
    private String name;
}
