package com.ssafy.travelquest.domain.party.dto;

import com.ssafy.travelquest.domain.party.entity.PartyStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PartyResponse {
    private Long partyId;
    private String title;
    private String description;
    private PartyStatus status;
    private Integer maxMember;

    public static PartyResponse of(Long partyId, String title, String description, PartyStatus status, Integer maxMember) {
        return PartyResponse.builder()
                .partyId(partyId)
                .title(title)
                .description(description)
                .status(status)
                .maxMember(maxMember)
                .build();
    }
}
