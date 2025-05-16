package com.ssafy.travelquest.global.common.event;

import com.ssafy.travelquest.domain.oauth.entity.OAuthProvider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserEvent {
    private Integer userId;
    private String sub;
    private OAuthProvider provider;
}
