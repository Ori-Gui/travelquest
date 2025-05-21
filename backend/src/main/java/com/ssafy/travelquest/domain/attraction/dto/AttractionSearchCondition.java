package com.ssafy.travelquest.domain.attraction.dto;

import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AttractionSearchCondition {
	@Min(1)
    private Integer sidoCode;
	@Min(1)
    private Integer gugunCode;
	@Min(1)
    private Integer contentTypeId;
    private String keyword;
}

