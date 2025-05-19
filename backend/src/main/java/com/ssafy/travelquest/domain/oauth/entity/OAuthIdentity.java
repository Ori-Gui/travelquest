package com.ssafy.travelquest.domain.oauth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class OAuthIdentity {

    private Long oauthIdentityId;

    private String sub;

    private OAuthProvider provider;

    private Long userId;

    public static OAuthIdentity of(String sub, OAuthProvider provider, Long userId) {
        return OAuthIdentity.builder()
                .sub(sub)
                .provider(provider)
                .userId(userId)
                .build();
    }
}
