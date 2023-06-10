package com.outfit.planner.system.product.service.domain;

import com.outfit.planner.system.product.service.domain.entity.Product;
import com.outfit.planner.system.product.service.domain.event.ProductCreatedEvent;

public interface ProductDomainService {

    ProductCreatedEvent validateAndInitiateProduct(Product product);

}
