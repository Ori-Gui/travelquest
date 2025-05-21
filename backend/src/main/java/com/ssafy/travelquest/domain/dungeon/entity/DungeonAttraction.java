package com.ssafy.travelquest.domain.dungeon.entity;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class DungeonAttraction {
    private Integer id;
    private Integer dungeonId;
    private Integer attractionId;
}
