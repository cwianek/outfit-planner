package com.outfit.planner.system.domain.ports.output.repository;

import com.outfit.planner.system.domain.outbox.model.ProductOutboxMessage;
import com.outfit.planner.system.outbox.OutboxStatus;

import java.util.List;

public interface ProductOutboxRepository {
    ProductOutboxMessage save(ProductOutboxMessage productOutboxMessage);
    List<ProductOutboxMessage> findByOutboxStatus(OutboxStatus outboxStatus);
}
