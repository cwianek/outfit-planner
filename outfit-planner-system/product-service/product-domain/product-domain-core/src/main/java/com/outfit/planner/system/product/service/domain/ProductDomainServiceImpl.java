package com.outfit.planner.system.product.service.domain;

import com.outfit.planner.system.product.service.domain.entity.Product;
import com.outfit.planner.system.product.service.domain.event.ProductCreatedEvent;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class ProductDomainServiceImpl implements ProductDomainService {
    @Override
    public ProductCreatedEvent validateAndInitiateProduct(Product product) {
        ProductCreatedEvent event = new ProductCreatedEvent();
        event.setProduct(product);
        event.setCreatedAt(ZonedDateTime.now(ZoneId.of(ZoneOffset.UTC.getId())));

        return event;
    }
}
