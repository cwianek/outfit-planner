package com.outfit.planner.system.product.service.dataaccess.product.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Table(name = "products")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {

    @Id
    private UUID id;
    private String category;
    private byte[] image;
    private String username;

}
