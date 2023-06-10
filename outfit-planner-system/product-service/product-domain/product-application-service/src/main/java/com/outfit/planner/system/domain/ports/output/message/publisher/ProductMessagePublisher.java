package com.outfit.planner.system.domain.ports.output.message.publisher;

import com.outfit.planner.system.domain.outbox.model.ProductOutboxMessage;
import com.outfit.planner.system.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface ProductMessagePublisher {
    void publish(ProductOutboxMessage productCreatedEvent, BiConsumer<ProductOutboxMessage, OutboxStatus> outboxCallback);
}
