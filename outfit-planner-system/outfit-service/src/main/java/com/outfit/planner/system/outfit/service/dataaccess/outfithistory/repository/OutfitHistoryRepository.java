package com.outfit.planner.system.outfit.service.dataaccess.outfithistory.repository;

import com.outfit.planner.system.outfit.service.dataaccess.outfithistory.entity.OutfitHistoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OutfitHistoryRepository extends MongoRepository<OutfitHistoryEntity, String> {
    Optional<OutfitHistoryEntity> findOneByWearDateAndOutfitId(LocalDate wearDate, String outfitId);
    List<OutfitHistoryEntity> findAllByWearDateAndOutfitIdIn(LocalDate wearDate, List<String> outfitsIds);
    List<OutfitHistoryEntity> findAllByOutfitIdIn(List<String> outfitsIds);
}
