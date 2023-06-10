package com.outfit.planner.system.product.service.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class Product {

    private UUID id;
    private String category;
    private byte[] image;
    private String username;

}
