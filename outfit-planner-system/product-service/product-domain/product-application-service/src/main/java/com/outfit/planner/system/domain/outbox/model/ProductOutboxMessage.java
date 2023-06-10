package com.outfit.planner.system.domain.outbox.model;

import com.outfit.planner.system.outbox.OutboxStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
public class ProductOutboxMessage {

    private UUID id;
    private OutboxStatus outboxStatus;
    private String payload;

//    private ZonedDateTime createdAt;
}
