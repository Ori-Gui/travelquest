package com.ssafy.travelquest.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.travelquest.domain.oauth.entity.OAuthIdentity;
import com.ssafy.travelquest.domain.oauth.entity.OAuthProvider;
import com.ssafy.travelquest.domain.oauth.service.OAuthService;
import com.ssafy.travelquest.domain.user.entity.User;
import com.ssafy.travelquest.domain.user.service.UserService;
import com.ssafy.travelquest.global.common.jwt.JwtProvider;
import com.ssafy.travelquest.global.common.jwt.JwtToken;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Oauth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final OAuthService oAuthService;
    private final UserService userService;
    @Value("${url.redirect.base}")
    private String REDIRECT_URL;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
        String sub = oidcUser.getSubject();
        OAuthProvider provider = ((CustomOidcUser) oidcUser).getProvider();

        Optional<OAuthIdentity> oAuthIdentity = oAuthService.findWithUserBySubAndProvider(sub,provider);
        Optional<User> user;

        if(oAuthIdentity.isPresent()){
            user = Optional.ofNullable(userService.getUser(oAuthIdentity.get().getUserId()));
            if(user.isEmpty()) {
                throw new NoSuchElementException("사용자를 찾을 수 없음");
            }
        } else {
            throw new NoSuchElementException("사용자를 찾을 수 없음");
        }

        JwtToken token = jwtProvider.provideTokens(
                user.get().getId(),
                user.get().getRole()
        );

        response.addCookie(createAccessTokenCookie(token.accessToken()));
        response.addCookie(createRefreshTokenCookie(token.refreshToken()));

        response.sendRedirect(REDIRECT_URL);
    }

    private Cookie createAccessTokenCookie(String accessToken) {
        Cookie cookie = new Cookie("accessToken", accessToken);
        cookie.setHttpOnly(false);
        cookie.setPath("/");
//        cookie.setSecure(true);
        cookie.setMaxAge(60 * 15); // 15분
        return cookie;
    }

    private Cookie createRefreshTokenCookie(String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24); // 24 hours
        return cookie;
    }
}
