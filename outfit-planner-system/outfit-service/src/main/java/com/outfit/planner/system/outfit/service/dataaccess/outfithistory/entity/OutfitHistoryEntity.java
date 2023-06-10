package com.outfit.planner.system.outfit.service.dataaccess.outfithistory.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "outfits_history")
public class OutfitHistoryEntity {

    @Id
    private String id;
    private String outfitId;
    private LocalDate wearDate;
    private WeatherConditions weatherConditions;

}
