package com.outfit.planner.system.product.service.domain.event;

import com.outfit.planner.system.product.service.domain.entity.Product;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ProductCreatedEvent {
    private Product product;
    private ZonedDateTime createdAt;
}
