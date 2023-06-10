package com.outfit.planner.system.outfit.service.external.prediction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.outfit.planner.system.outfit.service.config.PredictionServiceConfigData;
import com.outfit.planner.system.outfit.service.external.prediction.model.PredictionRequest;
import com.outfit.planner.system.outfit.service.external.prediction.model.PredictionResponse;
import com.outfit.planner.system.outfit.service.mapper.ObjectMapperUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PredictionClient {

    private final PredictionServiceConfigData predictionServiceConfigData;

    public PredictionClient(PredictionServiceConfigData predictionServiceConfigData) {
        this.predictionServiceConfigData = predictionServiceConfigData;
    }

    public PredictionResponse getPredictions(PredictionRequest predictionRequest) {
        try {
            return makeRequest(predictionRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private PredictionResponse makeRequest(PredictionRequest predictionRequest) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = ObjectMapperUtil.getMapper().writeValueAsString(predictionRequest);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        Object response = restTemplate.postForEntity(getPredictionUrl(), request, Object.class).getBody();
        return ObjectMapperUtil.getMapper().readValue(response.toString(), PredictionResponse.class);
    }

    private String getPredictionUrl() {
        return predictionServiceConfigData.getHostname() + ":" + predictionServiceConfigData.getPort() + "/predict";
    }
}
