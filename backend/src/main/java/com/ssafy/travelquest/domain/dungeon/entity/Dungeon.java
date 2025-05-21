package com.ssafy.travelquest.domain.dungeon.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Dungeon {
    private Integer id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer difficulty;
    private Integer maxPartySize;
    private Status status;
    private LocalDateTime createdAt;
}