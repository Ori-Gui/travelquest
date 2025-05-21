package com.ssafy.travelquest.domain.party.entity;

import com.ssafy.travelquest.domain.user.entity.JobCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class PartyMember {
    private Long id;
    private Long partyId;
    private Long userId;
    private JobCode jobCode;
    private LocalDateTime joinedAt;

    public static PartyMember of(Long partyId, Long userId, JobCode jobCode) {
        return PartyMember.builder()
                .partyId(partyId)
                .userId(userId)
                .jobCode(jobCode)
                .joinedAt(LocalDateTime.now())
                .build();
    }
}
