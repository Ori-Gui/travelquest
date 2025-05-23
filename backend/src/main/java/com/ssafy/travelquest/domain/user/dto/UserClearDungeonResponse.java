package com.ssafy.travelquest.domain.user.dto;

import java.time.LocalDate;

import groovy.transform.builder.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserClearDungeonResponse {
    private Long id;
    private String title;
    private String region;
    private String city;
    private LocalDate startDate;
    private LocalDate endDate;
}
