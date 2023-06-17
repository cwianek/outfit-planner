package com.outfit.planner.system.outfit.service.mapper;

import com.outfit.planner.system.kafka.product.avro.model.ProductAvroModel;
import com.outfit.planner.system.outfit.service.dataaccess.outbox.entity.ProductOutboxMessageEntity;
import com.outfit.planner.system.outfit.service.dataaccess.product.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMessagningDataMapper {

    public ProductEntity productAvroModelToProductModel(ProductAvroModel productAvroModel) {
        return ProductEntity.builder()
                .id(productAvroModel.getId())
                .category(productAvroModel.getCategory())
                .username(productAvroModel.getUsername())
                .build();
    }

    public ProductOutboxMessageEntity productAvroModelToProductOutboxMessageEntity(ProductAvroModel productAvroModel){
        return ProductOutboxMessageEntity.builder()
                .id(productAvroModel.getId())
                .payload(productAvroModelToProductModel(productAvroModel))
                .build();
    }

}
