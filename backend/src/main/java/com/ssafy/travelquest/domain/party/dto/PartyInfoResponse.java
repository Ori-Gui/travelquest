package com.ssafy.travelquest.domain.party.dto;

import com.ssafy.travelquest.domain.party.entity.PartyStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@Builder
public class PartyInfoResponse {

    private Long partyId;
    private String title;
    private String description;
    private PartyStatus status;
    private Integer maxMember;
    private LocalDateTime createdAt;
    private Integer currentMembers;

    private List<JobRequirement> jobRequirements;
}
