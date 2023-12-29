package com.outfit.planner.system.domain;

import com.outfit.planner.system.domain.dto.create.CreateProductRequest;
import com.outfit.planner.system.domain.dto.create.GetProductsCriteria;
import com.outfit.planner.system.domain.ports.input.service.ProductApplicationService;
import com.outfit.planner.system.domain.ports.output.repository.ProductRepository;
import com.outfit.planner.system.product.service.domain.entity.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class ProductMockService {

    private final ProductApplicationService productApplicationService;
    private final ProductRepository productRepository;

    @Autowired
    public ProductMockService(ProductApplicationService productApplicationService, ProductRepository productRepository) {
        this.productApplicationService = productApplicationService;
        this.productRepository = productRepository;
    }

    @PostConstruct
    public void createMocks() {
        if (areMocksAlreadyCreated()) {
            return;
        }

        String folderPath = "mock/products";
        try {
            iterateResourceFolders(folderPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean areMocksAlreadyCreated() {
        GetProductsCriteria criteria = new GetProductsCriteria();
        criteria.setUsername("mock");
        List<Product> products = productRepository.getProducts(criteria);

        return !products.isEmpty();
    }

    public void iterateResourceFolders(String folderPath) throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        String searchPath = "classpath*:" + folderPath + "/**/*.{jpg,jpeg}";

        Resource[] resources = resolver.getResources(searchPath);

        for (Resource resource : resources) {
            createProduct(resource, folderPath);
        }
    }

    private void createProduct(Resource resource, String folderPath) throws IOException {
        String categoryName = extractCategoryName(resource, folderPath);

        byte[] imageBytes = readImage(resource);

        String image = Base64.getEncoder().encodeToString(imageBytes);
        createMockedProduct(categoryName.toUpperCase(), image);
    }

    private String extractCategoryName(Resource resource, String folderPath) throws IOException {
        String entryName = resource.getURL().toString();
        int startIndex = entryName.indexOf(folderPath + "/") + folderPath.length() + 1;
        int endIndex = entryName.lastIndexOf("/");
        return entryName.substring(startIndex, endIndex);
    }

    private byte[] readImage(Resource resource) throws IOException {
        return resource.getInputStream().readAllBytes();
    }

    public void createMockedProduct(String category, String image) {
        CreateProductRequest request = new CreateProductRequest();
        request.setId(UUID.randomUUID());
        request.setUsername("mock");
        request.setImage(image);
        request.setCategory(category);

        productApplicationService.createProduct(request);
    }
}