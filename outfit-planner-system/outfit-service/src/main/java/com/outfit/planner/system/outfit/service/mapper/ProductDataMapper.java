package com.outfit.planner.system.outfit.service.mapper;

import com.outfit.planner.system.outfit.service.dataaccess.product.entity.ProductEntity;
import com.outfit.planner.system.outfit.service.dto.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductDataMapper {
    public ProductDto productEntityToProductDto(ProductEntity productEntity){
        return ProductDto.builder()
                .id(productEntity.getId())
                .category(productEntity.getCategory())
                .build();
    }

}
