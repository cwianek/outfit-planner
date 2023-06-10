package com.outfit.planner.system.product.service.dataaccess.product.outbox;

import com.outfit.planner.system.outbox.OutboxStatus;
import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_outbox")
@Entity
public class ProductOutboxEntity {
    @Id
    private UUID id;
    private String payload;
    @Enumerated(EnumType.STRING)
    private OutboxStatus outboxStatus;
    private ZonedDateTime createdAt;
    @Version
    private int version;
}
