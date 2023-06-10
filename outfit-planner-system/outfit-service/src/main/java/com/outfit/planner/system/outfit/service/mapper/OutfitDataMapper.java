package com.outfit.planner.system.outfit.service.mapper;

import com.outfit.planner.system.outfit.service.business.model.CreateOutfitRequest;
import com.outfit.planner.system.outfit.service.dataaccess.outfit.entity.OutfitEntity;
import com.outfit.planner.system.outfit.service.dto.OutfitDto;
import com.outfit.planner.system.outfit.service.dto.ProductDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OutfitDataMapper {

    private final ProductDataMapper productDataMapper;

    public OutfitDataMapper(ProductDataMapper productDataMapper) {
        this.productDataMapper = productDataMapper;
    }

    public OutfitEntity createOutfitRequestToOutfitEntity(CreateOutfitRequest request) {
        return OutfitEntity.builder()
                .username(request.getUsername())
                .build();
    }

    public OutfitDto outfitEntityToOutfitDto(OutfitEntity outfitEntity) {
        return OutfitDto.builder()
                .id(outfitEntity.getId())
                .products(convertProductEntitiesToProductsDto(outfitEntity))
                .build();
    }

    private List<ProductDto> convertProductEntitiesToProductsDto(OutfitEntity outfitEntity) {
        return outfitEntity.getProducts().stream().map(productDataMapper::productEntityToProductDto).collect(Collectors.toList());
    }

    public List<OutfitDto> outfitsResultToOutfitDTO(List<OutfitEntity> outfits, List<String> wornToday) {
        return outfits.stream()
                .map(outfitEntity -> getOutfitDto(wornToday, outfitEntity))
                .collect(Collectors.toList());
    }

    public List<String> outfitsEntitiesToIds(List<OutfitEntity> outfitEntities){
        return outfitEntities.stream()
                .map(OutfitEntity::getId)
                .collect(Collectors.toList());
    }

    private OutfitDto getOutfitDto(List<String> wornToday, OutfitEntity outfitEntity) {
        OutfitDto outfitDto = outfitEntityToOutfitDto(outfitEntity);
        outfitDto.setWornToday(wornToday.contains(outfitEntity.getId()));
        return outfitDto;
    }
}
