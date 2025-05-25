package com.ssafy.travelquest.domain.user.repository;

import java.time.Duration;

public interface RefreshTokenRepository {

    public void save(String userId, String refreshToken, long ttl);

    Long findByRefreshToken(String refreshToken);

    void deleteByRefreshToken(String refreshToken);
}
