package com.outfit.planner.system.outfit.service.dataaccess.outfit.repository;//package com.outfitplanner.cloth.service.domain.temp;

import com.outfit.planner.system.outfit.service.dataaccess.outfit.entity.OutfitEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OutfitRepository extends MongoRepository<OutfitEntity, String> {
    List<OutfitEntity> findAllByUsername(String username);
    List<OutfitEntity> findOutfitEntityByUsername(String username);
}
