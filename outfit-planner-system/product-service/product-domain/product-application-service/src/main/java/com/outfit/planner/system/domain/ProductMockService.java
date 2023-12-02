package com.outfit.planner.system.domain;

import com.outfit.planner.system.domain.dto.create.CreateProductRequest;
import com.outfit.planner.system.domain.ports.input.service.ProductApplicationService;
import com.outfit.planner.system.domain.ports.output.repository.ProductRepository;
import com.outfit.planner.system.domain.dto.create.GetProductsCriteria;
import com.outfit.planner.system.product.service.domain.entity.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Service
public class ProductMockService {

    private final ProductApplicationService productApplicationService;

    private final ProductRepository productRepository;

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
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private boolean areMocksAlreadyCreated() {
        GetProductsCriteria criteria = new GetProductsCriteria();
        criteria.setUsername("mock");
        List<Product> products = productRepository.getProducts(criteria);

        return !products.isEmpty();
    }

    public void iterateResourceFolders(String folderPath) throws IOException, URISyntaxException {
        Path path = Paths.get(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
        try (JarFile jarFile = new JarFile(path.toFile())) {
            crateMocks(folderPath, jarFile);
        }
    }

    private void crateMocks(String folderPath, JarFile jarFile) throws IOException {
        Enumeration<JarEntry> entries = jarFile.entries();

        List<JarEntry> entryList = Collections.list(entries);
        entryList.sort(Comparator.comparing(JarEntry::getName));

        for (JarEntry entry : entryList) {
            createProduct(folderPath, jarFile, entry);
        }
    }

    private void createProduct(String folderPath, JarFile jarFile, JarEntry entry) throws IOException {
        String entryName = entry.getName();
        if (entryName.startsWith(folderPath + "/") && !entry.isDirectory()) {
            int lastIndex = entryName.lastIndexOf("/");
            int secondLastIndex = entryName.lastIndexOf("/", lastIndex - 1);
            String categoryName = entryName.substring(secondLastIndex + 1, lastIndex);

            byte[] imageBytes = readImage(jarFile, entry);

            String image = Base64.getEncoder().encodeToString(imageBytes);
            createMockedProduct(categoryName.toUpperCase(), image);
        }
    }

    private static byte[] readImage(JarFile jarFile, JarEntry entry) throws IOException {
        byte[] imageBytes;
        try (var inputStream = jarFile.getInputStream(entry)) {
            imageBytes = inputStream.readAllBytes();
        }
        return imageBytes;
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
