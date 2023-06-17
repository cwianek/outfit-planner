package com.outfit.planner.system.product.service.messaging.mapper;

import com.outfit.planner.system.kafka.product.avro.model.ProductAvroModel;
import com.outfit.planner.system.product.service.domain.event.ProductCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public class ProductMessagingDataMapper {

    public ProductAvroModel productCreatedEventToProductAvroModel(ProductCreatedEvent productCreatedEvent, String messageId){
        return ProductAvroModel.newBuilder()
                .setMessageId(messageId)
                .setId(productCreatedEvent.getProduct().getId().toString())
                .setCategory(productCreatedEvent.getProduct().getCategory())
                .setUsername(productCreatedEvent.getProduct().getUsername())
                .build();
    }

}
