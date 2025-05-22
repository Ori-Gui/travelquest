package com.ssafy.travelquest.domain.quest.entity;

import java.time.LocalDateTime;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Quest {
    private Integer id;
    private Integer dungeonId;
    private Integer attractionId;
    private String title;
    private String description;
    private Integer orderIndex;
    private QuestStatus status;
    private LocalDateTime createdAt;
}
