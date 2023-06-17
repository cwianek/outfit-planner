package com.outfit.planner.system.outfit.service.dataaccess.outbox.entity;//package com.outfitplanner.cloth.service.domain.temp;
import com.outfit.planner.system.outbox.OutboxStatus;
import com.outfit.planner.system.outfit.service.dataaccess.product.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "product_outbox")
public class ProductOutboxMessageEntity {

    @MongoId
    private String id;
    private OutboxStatus outboxStatus;
    private ProductEntity payload;

}
