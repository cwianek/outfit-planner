package com.outfit.planner.system.domain.dto.create;

import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Data
public class ProductDto extends RepresentationModel<ProductDto> {
    private String id;
    private String name;
    private String category;
    private String username;
}
