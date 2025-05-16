package com.ssafy.travelquest.global.security;

import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import com.ssafy.travelquest.domain.oauth.entity.OAuthProvider;


public class CustomOidcUser extends DefaultOidcUser {
    private final OAuthProvider provider;

    public CustomOidcUser(OidcUser delegate, OAuthProvider provider) {
        super(delegate.getAuthorities(), delegate.getIdToken(), delegate.getUserInfo());
        this.provider = provider;
    }

    public OAuthProvider getProvider() {
        return provider;
    }
}