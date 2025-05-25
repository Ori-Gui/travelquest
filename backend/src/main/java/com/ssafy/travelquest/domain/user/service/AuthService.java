package com.ssafy.travelquest.domain.user.service;

import com.ssafy.travelquest.domain.user.dto.AccessTokenResponse;
import com.ssafy.travelquest.domain.user.entity.User;
import com.ssafy.travelquest.domain.user.repository.RefreshTokenRepository;
import com.ssafy.travelquest.domain.user.repository.UserRepository;
import com.ssafy.travelquest.global.common.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtUtils;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;


    public AccessTokenResponse refreshAccessToken(String refreshToken) {
        // 1) 만료 검사
        if (!jwtUtils.validateToken(refreshToken)) {
            refreshTokenRepository.deleteByRefreshToken(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }

        // 2) DB에서 토큰 찾아서
        Long userId = refreshTokenRepository.findByRefreshToken(refreshToken);
        User user = userRepository.findById(userId);

        String accessToken = jwtUtils.provideTokens(
                user.getId(),
                user.getRole(),
                user.getRegistStatus()
        ).getAccessToken();

        return AccessTokenResponse.of(
                accessToken
        );
    }

}
