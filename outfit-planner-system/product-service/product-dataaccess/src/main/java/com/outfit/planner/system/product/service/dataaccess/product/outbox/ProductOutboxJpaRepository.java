package com.outfit.planner.system.product.service.dataaccess.product.outbox;

import com.outfit.planner.system.domain.outbox.model.ProductOutboxMessage;
import com.outfit.planner.system.outbox.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductOutboxJpaRepository extends JpaRepository<ProductOutboxEntity, UUID> {
    List<ProductOutboxMessage> findByOutboxStatus(OutboxStatus outboxStatus);

}
