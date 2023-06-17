package com.outfit.planner.system.outfit.service.api;


import com.outfit.planner.system.outfit.service.dataaccess.outfit.entity.OutfitEntity;
import com.outfit.planner.system.outfit.service.dataaccess.outfit.repository.OutfitRepository;
import com.outfit.planner.system.outfit.service.dto.OutfitDto;
import com.outfit.planner.system.outfit.service.mapper.OutfitDataMapper;
import com.outfit.planner.system.outfit.service.utils.Softmanx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@RestController
@RequestMapping(value = "/outfits/mock")
public class OutfitMockController {

    private final OutfitRepository outfitRepository;

    private final OutfitDataMapper outfitDataMapper;

    public OutfitMockController(OutfitRepository outfitRepository, OutfitDataMapper outfitDataMapper) {
        this.outfitRepository = outfitRepository;
        this.outfitDataMapper = outfitDataMapper;
    }

    @PostMapping(value = "/predictions")
    public ResponseEntity<List<OutfitDto>> predictOutfits() {
        List<OutfitEntity> outfits = outfitRepository.findAllByUsername("mock");
        List<OutfitDto> outfitsDTOs = outfitDataMapper.outfitsResultToOutfitDTO(outfits, List.of());

        List<Double> probabilities = Softmanx.softmax(outfitsDTOs.size());
        IntStream.range(0, probabilities.size())
                .forEach(i -> outfitsDTOs.get(i).setMatchProbability(probabilities.get(i)));

        return ResponseEntity.ok(outfitsDTOs);
    }



}
