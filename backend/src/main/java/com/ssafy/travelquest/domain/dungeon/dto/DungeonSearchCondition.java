package com.ssafy.travelquest.domain.dungeon.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class DungeonSearchCondition {
	@Min(1)
    private Integer sidoCode;
	@Min(1)
    private Integer gugunCode;
	@Min(1)
    private Integer contentTypeId;
    private String keyword;
    private LocalDate startDate;
    private LocalDate endDate;
}
