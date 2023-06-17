package com.outfit.planner.system.outfit.service.api;


import com.outfit.planner.system.outfit.service.business.OutfitsService;
import com.outfit.planner.system.outfit.service.business.model.CreateOutfitRequest;
import com.outfit.planner.system.outfit.service.business.model.PredictOutfitsRequest;
import com.outfit.planner.system.outfit.service.business.model.ToggleWearOutfitRequest;
import com.outfit.planner.system.outfit.service.dto.OutfitDto;
import com.outfit.planner.system.outfit.service.security.OutfitsServiceUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/outfits")
@PreAuthorize("isAuthenticated()")
public class OutfitController {

    private final OutfitsService outfitsService;
    public OutfitController(OutfitsService outfitsService) {
        this.outfitsService = outfitsService;
    }

    @PostMapping(value = "/predictions")
    @PreAuthorize("hasRole('APP_USER_ROLE') || hasAnyAuthority('SCOPE_APP_USRE_ROLE')")
    public ResponseEntity<List<OutfitDto>> predictOutfits(@RequestBody PredictOutfitsRequest predictOutfitsRequest) {
        predictOutfitsRequest.setUsername(getUsername());
        List<OutfitDto> outfits = outfitsService.predictOutfits(predictOutfitsRequest);

        return ResponseEntity.ok(outfits);
    }

    @PostMapping
    @PreAuthorize("hasRole('APP_USER_ROLE') || hasAnyAuthority('SCOPE_APP_USRE_ROLE')")
    public ResponseEntity<OutfitDto> createOutfit(@RequestBody CreateOutfitRequest createOutfitRequest) {
        createOutfitRequest.setUsername(getUsername());
        OutfitDto outfit = outfitsService.createOutfit(createOutfitRequest);

        return ResponseEntity.ok(outfit);
    }

    @PostMapping(value = "/toggle-wear")
    @PreAuthorize("hasRole('APP_USER_ROLE') || hasAnyAuthority('SCOPE_APP_USRE_ROLE')")
    public ResponseEntity<Boolean> toggleWear(@RequestBody ToggleWearOutfitRequest selectOutfitRequest) {
        selectOutfitRequest.setWearDate(LocalDate.now());
        boolean worn = outfitsService.toggleWorn(selectOutfitRequest);

        return ResponseEntity.ok(worn);
    }

    private String getUsername() {
        OutfitsServiceUser user = (OutfitsServiceUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return user.getUsername();
    }

}
