package com.ssafy.travelquest.domain.oauth.service;

import java.util.Optional;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.ssafy.travelquest.domain.oauth.entity.OAuthIdentity;
import com.ssafy.travelquest.domain.oauth.entity.OAuthProvider;
import com.ssafy.travelquest.domain.oauth.repository.OAuthRepository;
import com.ssafy.travelquest.global.common.event.CreateUserEvent;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OAuthService {
    private final OAuthRepository oauthRepository;

    @EventListener
    public void createOAuthIdentity(CreateUserEvent event) {
        oauthRepository.create(
                OAuthIdentity.builder()
                        .sub(event.getSub())
                        .provider(event.getProvider())
                        .userId(event.getUserId())
                        .build()
        );
    }

    public Optional<OAuthIdentity> findWithUserBySubAndProvider(String sub, OAuthProvider provider) {
        return oauthRepository.findWithUserBySubAndProvider(sub, provider);
    }
}
