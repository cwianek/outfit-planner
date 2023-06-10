package com.outfit.planner.system.outfit.service.dataaccess.outbox.repository;//package com.outfitplanner.cloth.service.domain.temp;

import com.outfit.planner.system.outbox.OutboxStatus;
import com.outfit.planner.system.outfit.service.dataaccess.outbox.entity.ProductOutboxMessageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductOutboxMessageRepository extends MongoRepository<ProductOutboxMessageEntity, String> {

    List<ProductOutboxMessageEntity> findByOutboxStatus(OutboxStatus outboxStatus);

}
