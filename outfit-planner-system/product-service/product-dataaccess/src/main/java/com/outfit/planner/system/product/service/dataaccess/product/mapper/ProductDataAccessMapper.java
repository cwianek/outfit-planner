package com.outfit.planner.system.product.service.dataaccess.product.mapper;

import com.outfit.planner.system.product.service.dataaccess.product.entity.ProductEntity;
import com.outfit.planner.system.product.service.domain.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductDataAccessMapper {

    public Product productEntityToProduct(ProductEntity productEntity){
        return Product.builder()
                .id(productEntity.getId())
                .category(productEntity.getCategory())
                .image(productEntity.getImage())
                .username(productEntity.getUsername())
                .build();
    }

    public ProductEntity productToProductEntity(Product product){
        return ProductEntity.builder()
                .id(product.getId())
                .category(product.getCategory())
                .image(product.getImage())
                .username(product.getUsername())
                .build();
    }
}
