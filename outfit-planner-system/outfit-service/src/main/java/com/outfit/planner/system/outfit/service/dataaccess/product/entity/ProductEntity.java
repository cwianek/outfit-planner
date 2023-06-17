package com.outfit.planner.system.outfit.service.dataaccess.product.entity;//package com.outfitplanner.cloth.service.domain.temp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "products")
public class ProductEntity {

    @Id
    private String id;
    private String category;
    private String username;
    private byte[] image;

}
