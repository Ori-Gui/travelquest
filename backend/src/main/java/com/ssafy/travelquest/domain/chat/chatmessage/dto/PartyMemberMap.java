package com.ssafy.travelquest.domain.chat.chatmessage.dto;

import com.ssafy.travelquest.domain.party.entity.PartyMember;
import com.ssafy.travelquest.domain.user.entity.JobCode;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class PartyMemberMap {
    private Long userId;
    private String name;
    private JobCode job;

    public static PartyMemberMap from(Long userId, String name, JobCode job) {
        return PartyMemberMap.builder()
                .userId(userId)
                .name(name)
                .job(job)
                .build();
    }

    public static PartyMemberMap of(PartyMember partyMember) {
        return PartyMemberMap.builder()
                .userId(partyMember.getUserId())
                .build();
    }
}
