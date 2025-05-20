package com.ssafy.travelquest.domain.party.entity;

import com.ssafy.travelquest.domain.user.entity.JobCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class PartyRoleRequirement {
    private Long id;
    private Long partyId;
    private JobCode jobCode;
    private Integer maxCount;

    public static PartyRoleRequirement of(Long partyId, JobCode jobCode, Integer maxCount) {
        return PartyRoleRequirement.builder()
                .partyId(partyId)
                .jobCode(jobCode)
                .maxCount(maxCount)
                .build();
    }
}
