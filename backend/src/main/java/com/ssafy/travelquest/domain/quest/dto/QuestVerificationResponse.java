package com.ssafy.travelquest.domain.quest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class QuestVerificationResponse {
    private Integer id;
    private String photoUrl;
    private String status;
    private LocalDateTime verifiedAt;
}
