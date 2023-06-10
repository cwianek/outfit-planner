package com.outfit.planner.system.domain.mapper;

import com.outfit.planner.system.product.service.domain.entity.Product;
import com.outfit.planner.system.domain.dto.create.CreateProductRequest;
import com.outfit.planner.system.domain.dto.create.ProductDto;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class ProductDataMapper {

    public Product createProductRequestToProduct(CreateProductRequest request) {
        return Product.builder()
                .id(request.getId())
                .category(request.getCategory())
                .image(request.getImage().getBytes())
                .username(request.getUsername())
                .build();
    }

    public ProductDto productToProductDto(Product product) {
        return ProductDto.builder()
                .id(product.getId().toString())
                .category(product.getCategory())
                .build();
    }

}
