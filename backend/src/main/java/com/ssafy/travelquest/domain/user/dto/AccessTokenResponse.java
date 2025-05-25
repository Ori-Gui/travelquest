package com.ssafy.travelquest.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccessTokenResponse {
    private String accessToken;

    public static AccessTokenResponse of(String accessToken) {
        return AccessTokenResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
