package com.outfit.planner.system.domain.dto.create;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateProductRequest {
    private UUID id;
    private String category;
    private String image;
    private String username;
}
