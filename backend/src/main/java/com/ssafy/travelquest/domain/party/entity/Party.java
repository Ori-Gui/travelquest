package com.ssafy.travelquest.domain.party.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class Party {
    private Long id;
    private Long dungeonId;
    private Long leaderId;
    private String title;
    private PartyStatus status;
    private Integer maxMember;
    private LocalDateTime createdAt;

    public static Party of(Long dungeonId, Long leaderId, String title, Integer maxMember) {
        return Party.builder()
                .dungeonId(dungeonId)
                .leaderId(leaderId)
                .title(title)
                .status(PartyStatus.MATCHING)
                .maxMember(maxMember)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
