package com.outfit.planner.system.outfit.service.external.prediction;

import com.outfit.planner.system.outfit.service.business.model.Geolocation;
import com.outfit.planner.system.outfit.service.dataaccess.outfithistory.entity.OutfitHistoryEntity;
import com.outfit.planner.system.outfit.service.dataaccess.outfithistory.entity.WeatherConditions;
import com.outfit.planner.system.outfit.service.dataaccess.outfithistory.repository.OutfitHistoryRepository;
import com.outfit.planner.system.outfit.service.dto.OutfitDto;
import com.outfit.planner.system.outfit.service.external.prediction.model.PredictionRequest;
import com.outfit.planner.system.outfit.service.external.prediction.model.PredictionResponse;
import com.outfit.planner.system.outfit.service.external.weather.WeatherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
class PredictionServiceImpl implements PredictionService {

    private final PredictionClient predictionClient;

    private final OutfitHistoryRepository outfitHistoryRepository;

    private final WeatherService weatherService;

    PredictionServiceImpl(PredictionClient predictionClient, OutfitHistoryRepository outfitHistoryRepository, WeatherService weatherService) {
        this.predictionClient = predictionClient;
        this.outfitHistoryRepository = outfitHistoryRepository;
        this.weatherService = weatherService;
    }

    @Override
    public void appendPredictions(List<OutfitDto> outfitDtos, Geolocation geolocation) {
        Map<String, Double> predictions = getPredictions(outfitDtos, geolocation);

        outfitDtos.forEach(outfitDto -> {
            Double matchProbability = predictions.get(outfitDto.getId());
            outfitDto.setMatchProbability(matchProbability);
        });
    }

    private Map<String, Double> getPredictions(List<OutfitDto> outfitDtos, Geolocation geolocation) {
        List<String> outfitsIds = mapOutfitDtosToIds(outfitDtos);
        List<OutfitHistoryEntity> historyEntities = outfitHistoryRepository.findAllByOutfitIdIn(outfitsIds);

        if (historyEntities.isEmpty()) {
            return Map.of();
        }

        WeatherConditions weatherConditions = weatherService.getWeather(geolocation);
        PredictionRequest predictionRequest = createPredictionRequest(historyEntities, weatherConditions);
        PredictionResponse response = predictionClient.getPredictions(predictionRequest);

        return response.getPredictions();
    }

    private PredictionRequest createPredictionRequest(List<OutfitHistoryEntity> historyEntities, WeatherConditions weatherConditions) {
        PredictionRequest predictionRequest = new PredictionRequest();
        predictionRequest.setOutfits(historyEntities);
        predictionRequest.setWeatherConditions(weatherConditions);

        return predictionRequest;
    }

    private List<String> mapOutfitDtosToIds(List<OutfitDto> outfitDtos) {
        return outfitDtos.stream()
                .map(OutfitDto::getId)
                .collect(Collectors.toList());
    }

}
