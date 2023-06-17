package com.outfit.planner.system.outfit.service.mock;

import com.outfit.planner.system.outfit.service.business.OutfitsService;
import com.outfit.planner.system.outfit.service.business.model.CreateOutfitRequest;
import com.outfit.planner.system.outfit.service.dataaccess.outfit.entity.OutfitEntity;
import com.outfit.planner.system.outfit.service.dataaccess.outfit.repository.OutfitRepository;
import com.outfit.planner.system.outfit.service.dataaccess.product.entity.ProductEntity;
import com.outfit.planner.system.outfit.service.dataaccess.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.outfit.planner.system.outfit.service.security.Constants.MOCK;

@Slf4j
@Component
public class OutfitsMockScheduler {

    private final OutfitsService outfitsService;
    private final ProductRepository productRepository;
    private final OutfitRepository outfitRepository;

    public OutfitsMockScheduler(OutfitsService outfitsService, ProductRepository productRepository, OutfitRepository outfitRepository) {
        this.outfitsService = outfitsService;
        this.productRepository = productRepository;
        this.outfitRepository = outfitRepository;
    }

    @Transactional
    @Scheduled(fixedRate = 20000)
    public void createMockedOutfits() {
        List<OutfitEntity> anyOutfits = outfitRepository.findOutfitEntityByUsername(MOCK);
        if (!anyOutfits.isEmpty()) {
            return;
        }

        Map<String, List<ProductEntity>> productsByCategory = productRepository.findAllByUsername(MOCK).stream()
                .collect(Collectors.groupingBy(ProductEntity::getCategory));

        Integer size = getOutfitsSize(productsByCategory);
        Set<String> categories = productsByCategory.keySet();

        prepareOutfits(productsByCategory, size, categories)
                .forEach(this::createOutfit);
    }

    private static Stream<List<ProductEntity>> prepareOutfits(Map<String, List<ProductEntity>> productsByCategory, Integer size, Set<String> categories) {
        return IntStream.range(0, size).mapToObj(i -> categories.stream()
                .map(productsByCategory::get)
                .filter(productsList -> productsList.size() > i)
                .map(productsList -> productsList.get(i))
                .collect(Collectors.toList()));
    }

    private static Integer getOutfitsSize(Map<String, List<ProductEntity>> productsByCategory) {
        return productsByCategory.values().stream()
                .map(List::size)
                .reduce(Integer::max)
                .orElse(0);
    }

    private void createOutfit(List<ProductEntity> products) {
        List<String> ids = products.stream()
                .map(ProductEntity::getId)
                .collect(Collectors.toList());

        CreateOutfitRequest request = new CreateOutfitRequest();
        request.setProductsIds(ids);
        request.setUsername(MOCK);
        outfitsService.createOutfit(request);
    }
}
