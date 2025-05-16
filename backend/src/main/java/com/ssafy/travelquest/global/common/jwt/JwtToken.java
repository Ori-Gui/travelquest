package com.ssafy.travelquest.global.common.jwt;

import lombok.Builder;

@Builder
public record JwtToken(
        String accessToken,
        String refreshToken
) {
}
