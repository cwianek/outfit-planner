package com.outfit.planner.system.outfit.service.dataaccess.outfit.entity;//package com.outfitplanner.cloth.service.domain.temp;
//
import com.outfit.planner.system.outfit.service.dataaccess.product.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "outfits")
public class OutfitEntity {

    @Id
    private String id;
    private String username;
    private List<ProductEntity> products;

}
