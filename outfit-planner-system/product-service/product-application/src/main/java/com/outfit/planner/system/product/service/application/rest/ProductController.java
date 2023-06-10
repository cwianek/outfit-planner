package com.outfit.planner.system.product.service.application.rest;


import com.outfit.planner.system.domain.dto.create.CreateProductRequest;
import com.outfit.planner.system.domain.dto.create.ProductDto;
import com.outfit.planner.system.domain.ports.input.service.ProductApplicationService;
import com.outfit.planner.system.product.service.application.security.ProductServiceUser;
import com.outfit.planner.system.product.service.domain.GetProductsCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Slf4j
@RestController
@RequestMapping(value = "/products")
@PreAuthorize("isAuthenticated()")
public class ProductController {

    private final ProductApplicationService productApplicationService;

    public ProductController(ProductApplicationService productApplicationService) {
        this.productApplicationService = productApplicationService;
    }

    @GetMapping
    @PreAuthorize("hasRole('APP_USER_ROLE') || hasAnyAuthority('SCOPE_APP_USRE_ROLE')")
    public ResponseEntity<List<ProductDto>> getProducts() {
        GetProductsCriteria criteria = GetProductsCriteria.builder()
                .username(getUsername())
                .build();
        List<ProductDto> products = productApplicationService.getProducts(criteria);
        products.forEach(product -> product.add(getImageLink(product)));

        return ResponseEntity.ok(products);
    }

    @PostMapping
    @PreAuthorize("hasRole('APP_USER_ROLE') || hasAnyAuthority('SCOPE_APP_USRE_ROLE')")
    public ResponseEntity<ProductDto> createProduct(@RequestBody CreateProductRequest createProductRequest) {
        createProductRequest.setId(UUID.randomUUID());
        createProductRequest.setUsername(getUsername());

        ProductDto product = productApplicationService.createProduct(createProductRequest);
        product.add(getImageLink(product));

        return ResponseEntity.ok(product);
    }
    @GetMapping(
            value = "/{id}/image",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    @PreAuthorize("hasRole('APP_USER_ROLE') || hasAnyAuthority('SCOPE_APP_USRE_ROLE')")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") String id) {
        byte[] image = productApplicationService.getImage(id);
        byte[] decoded = Base64.getDecoder().decode(image);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"\"")
                .body(decoded);
    }
    private String getUsername() {
        ProductServiceUser user = (ProductServiceUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return user.getUsername();
    }
    private static Link getImageLink(ProductDto product) {
        return linkTo(methodOn(ProductController.class)
                .getImage(product.getId()))
                .withRel("image");
    }
}
