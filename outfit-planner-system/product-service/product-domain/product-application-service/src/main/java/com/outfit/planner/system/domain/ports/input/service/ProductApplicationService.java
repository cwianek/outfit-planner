package com.outfit.planner.system.domain.ports.input.service;

import com.outfit.planner.system.domain.dto.create.CreateProductRequest;
import com.outfit.planner.system.domain.dto.create.GetProductsCriteria;
import com.outfit.planner.system.domain.dto.create.ProductDto;
import jakarta.validation.Valid;

import java.util.List;

public interface ProductApplicationService {

    List<ProductDto> getProducts(GetProductsCriteria criteria);
    ProductDto createProduct(@Valid CreateProductRequest createProductRequest);
    byte[] getImage(String productId);
    byte[] getImage(String productId, String username);

}
