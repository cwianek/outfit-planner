package com.outfit.planner.system.product.service.dataaccess.product.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.support.SecurityContextProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.security.Principal;
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

//    @PrePersist
//    protected void onPersist() {
//        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        this.userName = principal.getUsername();
//    }
}
