package com.outfit.planner.system.product.service.application.rest;


import com.outfit.planner.system.domain.dto.create.ProductDto;
import com.outfit.planner.system.domain.ports.input.service.ProductApplicationService;
import com.outfit.planner.system.product.service.domain.GetProductsCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping(value = "/products/mock")
public class MockProductController {

    private final ProductApplicationService productApplicationService;

    public MockProductController(ProductApplicationService productApplicationService) {
        this.productApplicationService = productApplicationService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        GetProductsCriteria criteria = GetProductsCriteria.builder()
                .username("mock")
                .build();
        List<ProductDto> products = productApplicationService.getProducts(criteria);
        products.forEach(product -> product.add(getImageLink(product)));

        return ResponseEntity.ok(products);
    }

    @GetMapping(
            value = "/{id}/image",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public ResponseEntity<byte[]> getImage(@PathVariable("id") String id) {
        byte[] image = productApplicationService.getImage(id, "mock");
        byte[] decoded = Base64.getDecoder().decode(image);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"\"")
                .body(decoded);
    }

    private static Link getImageLink(ProductDto product) {
        return linkTo(methodOn(ProductController.class)
                .getImage(product.getId()))
                .withRel("image");
    }

}
