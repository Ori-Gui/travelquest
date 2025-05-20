package com.ssafy.travelquest.domain.user.dto;

import com.ssafy.travelquest.domain.user.entity.JobCode;
import com.ssafy.travelquest.domain.user.entity.MBTI;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MbtiResultResponse {
    private MBTI mbti;
    private JobCode jobCode;
    private String jobDisplayName;
    private String mbtiDescription;

    public static MbtiResultResponse of(MBTI mbti, JobCode jobCode, String jobDisplayName, String mbtiDescription) {
        return MbtiResultResponse.builder()
                .mbti(mbti)
                .jobCode(jobCode)
                .jobDisplayName(jobDisplayName)
                .mbtiDescription(mbtiDescription)
                .build();
    }
}
