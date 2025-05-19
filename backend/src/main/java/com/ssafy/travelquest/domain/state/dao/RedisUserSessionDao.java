package com.ssafy.travelquest.domain.state.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@RequiredArgsConstructor
public class RedisUserSessionDao {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String USER_PREFIX = "user:";
    private static final String PARTY_PREFIX = "party:";

    public void saveUserParty(String userId, String partyId) {
        redisTemplate.opsForValue().set(USER_PREFIX + userId, partyId);
        redisTemplate.opsForSet().add(PARTY_PREFIX + partyId, userId);
    }

    public void removeUserParty(String userId) {
        String key = USER_PREFIX + userId;
        String partyId = (String) redisTemplate.opsForValue().get(key);
        redisTemplate.delete(key);

        if (partyId != null) {
            redisTemplate.opsForSet().remove(PARTY_PREFIX + partyId, userId);
        }
    }

    public Set<Object> getPartyMembers(String partyId) {
        return redisTemplate.opsForSet().members(PARTY_PREFIX + partyId);
    }

    public String getPartyByUser(String userId) {
        return (String) redisTemplate.opsForValue().get(USER_PREFIX + userId);
    }
}

