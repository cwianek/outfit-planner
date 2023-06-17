package com.outfit.planner.system.outfit.service.dataaccess.product.repository;//package com.outfitplanner.cloth.service.domain.temp;

import com.outfit.planner.system.outfit.service.dataaccess.product.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<ProductEntity, String> {

    List<ProductEntity> findAllByUsername(String username);

}
