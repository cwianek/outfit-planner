package com.outfit.planner.system.domain.ports.input.service;

import com.outfit.planner.system.domain.dto.create.CreateProductRequest;
import com.outfit.planner.system.domain.dto.create.ProductDto;
import com.outfit.planner.system.product.service.domain.GetProductsCriteria;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public interface ProductApplicationService {

    List<ProductDto> getProducts(GetProductsCriteria criteria);
    ProductDto createProduct(@Valid CreateProductRequest createProductRequest);
    byte[] getImage(String productId);
    byte[] getImage(String productId, String username);

}
