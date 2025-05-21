package com.ssafy.travelquest.domain.dungeon.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import com.ssafy.travelquest.domain.dungeon.entity.Status;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class DungeonCreateDto {
    @NotBlank
    private String title;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotNull
    @Min(1)
    private Integer maxPartySize;
    @NotNull
    private Status status;
}
