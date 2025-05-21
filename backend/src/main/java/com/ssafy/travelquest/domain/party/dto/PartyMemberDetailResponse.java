package com.ssafy.travelquest.domain.party.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Builder
public class PartyMemberDetailResponse {
    private Long id;
    private String nickname;
    private String job;
    private String jobName;
    private String mbti;
    private boolean isLeader;

    public static PartyMemberDetailResponse of(Long id, String nickname, String job, String jobName, String mbti, boolean isLeader) {
        return PartyMemberDetailResponse.builder()
                .id(id)
                .nickname(nickname)
                .job(job)
                .jobName(jobName)
                .mbti(mbti)
                .isLeader(isLeader)
                .build();
    }
}

