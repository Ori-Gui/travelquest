package com.ssafy.travelquest.domain.quest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class QuestVerificationInfoDto {
    private Integer id;
    private Integer questId;
    private Integer partyId;
    private Long verifiedByUserId;
    private String photoUrl;
    private String status;
    private LocalDateTime verifiedAt;
}