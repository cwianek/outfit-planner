package com.outfit.planner.system.outfit.service.business.model;

import lombok.Data;

import java.util.List;

@Data
public class CreateOutfitRequest {
    private List<String> productsIds;
    private String username;
}
