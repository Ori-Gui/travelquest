package com.ssafy.travelquest.domain.party.dto;

import java.time.LocalDateTime;

import com.ssafy.travelquest.domain.user.entity.JobCode;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PartyMemberInfo {
	private Long id;
    private Long partyId;
    private Long userId;
    private JobCode jobCode;
    private String profileImage;
    private LocalDateTime joinedAt;
}
