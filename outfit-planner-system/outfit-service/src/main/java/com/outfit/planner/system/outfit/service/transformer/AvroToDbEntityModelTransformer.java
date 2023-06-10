//package com.outfit.planner.system.outfit.service.transformer;
//
//import com.outfit.planner.system.kafka.product.avro.model.ProductAvroModel;
//import com.outfit.planner.system.outfit.service.dataaccess.entity.ProductEntity;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AvroToDbEntityModelTransformer {
//
//    public ProductEntity productAvroModelToProductEntity(ProductAvroModel productAvroModel){
//        return ProductEntity.builder()
//                .id(productAvroModel.getId())
//                .category(productAvroModel.getCategory())
//                .name(productAvroModel.getName())
//                .build();
//    }
//}
