package com.ssafy.travelquest.domain.quest.entity;

import java.time.LocalDateTime;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class QuestVerification {
    private Integer id;
    private Integer questId;
    private Integer partyId;
    private Long verifiedByUserId;
    private String photoUrl;
    private QuestStatus status;
    private LocalDateTime verifiedAt;
}

