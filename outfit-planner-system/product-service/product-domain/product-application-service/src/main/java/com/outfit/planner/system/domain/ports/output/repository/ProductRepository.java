package com.outfit.planner.system.domain.ports.output.repository;

import com.outfit.planner.system.domain.dto.create.GetProductsCriteria;
import com.outfit.planner.system.product.service.domain.entity.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    List<Product> getProducts(GetProductsCriteria criteria);
    Product createProduct(Product product);
    Optional<byte[]> getImage(UUID id);
    Optional<byte[]> getImage(UUID id, String username);
}
