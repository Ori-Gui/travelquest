package com.ssafy.travelquest.domain.party.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@Builder
public class JobRequirement {
    private String jobClassCode;
    private Integer maxCount;
    private Integer currentCount;
}
