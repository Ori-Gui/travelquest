package com.ssafy.travelquest.domain.party.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PartyStatusUpdateRequest {
    // body JSON 으로 { "status": "IN_PROGRESS" } 처럼 받습니다
    private String status;
}