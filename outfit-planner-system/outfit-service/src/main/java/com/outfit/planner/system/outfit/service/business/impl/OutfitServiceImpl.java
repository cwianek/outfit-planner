package com.outfit.planner.system.outfit.service.business.impl;

import com.outfit.planner.system.outfit.service.business.OutfitsService;
import com.outfit.planner.system.outfit.service.business.model.CreateOutfitRequest;
import com.outfit.planner.system.outfit.service.business.model.PredictOutfitsRequest;
import com.outfit.planner.system.outfit.service.business.model.ToggleWearOutfitRequest;
import com.outfit.planner.system.outfit.service.dataaccess.outfit.entity.OutfitEntity;
import com.outfit.planner.system.outfit.service.dataaccess.outfit.repository.OutfitRepository;
import com.outfit.planner.system.outfit.service.dataaccess.outfithistory.entity.OutfitHistoryEntity;
import com.outfit.planner.system.outfit.service.dataaccess.outfithistory.entity.WeatherConditions;
import com.outfit.planner.system.outfit.service.dataaccess.outfithistory.repository.OutfitHistoryRepository;
import com.outfit.planner.system.outfit.service.dataaccess.product.entity.ProductEntity;
import com.outfit.planner.system.outfit.service.dataaccess.product.repository.ProductRepository;
import com.outfit.planner.system.outfit.service.dto.OutfitDto;
import com.outfit.planner.system.outfit.service.external.prediction.PredictionService;
import com.outfit.planner.system.outfit.service.external.weather.WeatherService;
import com.outfit.planner.system.outfit.service.mapper.OutfitDataMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OutfitServiceImpl implements OutfitsService {

    private final ProductRepository productRepository;
    private final OutfitRepository outfitRepository;
    private final OutfitDataMapper outfitDataMapper;
    private final OutfitHistoryRepository outfitHistoryRepository;
    private final WeatherService weatherService;
    private final PredictionService predictionService;


    public OutfitServiceImpl(ProductRepository productRepository, OutfitRepository outfitRepository, OutfitDataMapper outfitDataMapper, OutfitHistoryRepository outfitHistoryRepository, WeatherService weatherService, PredictionService predictionService) {
        this.productRepository = productRepository;
        this.outfitRepository = outfitRepository;
        this.outfitDataMapper = outfitDataMapper;
        this.outfitHistoryRepository = outfitHistoryRepository;
        this.weatherService = weatherService;
        this.predictionService = predictionService;
    }

    @Override
    public OutfitDto createOutfit(CreateOutfitRequest request) {
        ArrayList<ProductEntity> productsList = new ArrayList<>();
        productRepository.findAllById(request.getProductsIds())
                .forEach(productsList::add);

        OutfitEntity outfit = outfitDataMapper.createOutfitRequestToOutfitEntity(request);
        outfit.setProducts(productsList);
        OutfitEntity saved = outfitRepository.save(outfit);

        return outfitDataMapper.outfitEntityToOutfitDto(saved);
    }

    @Override
    public List<OutfitDto> predictOutfits(PredictOutfitsRequest request) {
        List<OutfitEntity> outfits = outfitRepository.findAllByUsername(request.getUsername());
        List<String> wornToday = getOutfitsIdsWornToday(outfits);
        List<OutfitDto> outfitDTOs = outfitDataMapper.outfitsResultToOutfitDTO(outfits, wornToday);
        predictionService.appendPredictions(outfitDTOs, request.getGeolocation());

        return outfitDTOs;
    }

    private List<String> getOutfitsIdsWornToday(List<OutfitEntity> outfitEntities) {
        List<String> ids = outfitDataMapper.outfitsEntitiesToIds(outfitEntities);

        return outfitHistoryRepository.findAllByWearDateAndOutfitIdIn(LocalDate.now(), ids).stream()
                .map(OutfitHistoryEntity::getOutfitId)
                .collect(Collectors.toList());
    }

    @Override
    public boolean toggleWorn(ToggleWearOutfitRequest request) {
        WeatherConditions weatherConditions = weatherService.getWeather(request.getGeolocation());

        Optional<OutfitHistoryEntity> existingHistory = outfitHistoryRepository.findOneByWearDateAndOutfitId(request.getWearDate(), request.getId());
        if (existingHistory.isPresent()) {
            outfitHistoryRepository.delete(existingHistory.get());
            return false;
        } else {
            saveOutfitHistory(request, weatherConditions);
            return true;
        }
    }

    private void saveOutfitHistory(ToggleWearOutfitRequest request, WeatherConditions weatherConditions) {
        OutfitHistoryEntity outfitHistory = new OutfitHistoryEntity();
        outfitHistory.setWearDate(request.getWearDate());
        outfitHistory.setOutfitId(request.getId());
        outfitHistory.setWeatherConditions(weatherConditions);

        outfitHistoryRepository.save(outfitHistory);
    }
}
