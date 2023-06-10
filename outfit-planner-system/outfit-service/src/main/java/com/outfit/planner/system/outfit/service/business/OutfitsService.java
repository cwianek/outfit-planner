package com.outfit.planner.system.outfit.service.business;

import com.outfit.planner.system.outfit.service.business.model.CreateOutfitRequest;
import com.outfit.planner.system.outfit.service.business.model.PredictOutfitsRequest;
import com.outfit.planner.system.outfit.service.business.model.ToggleWearOutfitRequest;
import com.outfit.planner.system.outfit.service.dto.OutfitDto;

import java.util.List;

public interface OutfitsService {

    OutfitDto createOutfit(CreateOutfitRequest request);
    List<OutfitDto> predictOutfits(PredictOutfitsRequest username);
    boolean toggleWorn(ToggleWearOutfitRequest request);
}
