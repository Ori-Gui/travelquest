package com.ssafy.travelquest.domain.state.dao;

import com.ssafy.travelquest.domain.state.dto.ChatRoomUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@RequiredArgsConstructor
public class RedisUserSessionDao {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String USER_PREFIX = "user:";   // user:{userId} → ChatRoomUserDto
    private static final String PARTY_PREFIX = "party:"; // party:{partyId} → Set<ChatRoomUserDto>

    // ✅ 파티 참가
    public void saveUserParty(Long userId, ChatRoomUserDto dto) {
        redisTemplate.opsForValue().set(USER_PREFIX + userId, dto);
        redisTemplate.opsForSet().add(PARTY_PREFIX + dto.getPartyId(), dto);
    }

    // ✅ 파티 퇴장
    public void removeUserParty(Long userId) {
        String userKey = USER_PREFIX + userId;
        ChatRoomUserDto dto = (ChatRoomUserDto) redisTemplate.opsForValue().get(userKey);

        if (dto != null) {
            redisTemplate.delete(userKey);

            Set<Object> members = redisTemplate.opsForSet().members(PARTY_PREFIX + dto.getPartyId());
            if (members != null) {
                for (Object member : members) {
                    ChatRoomUserDto m = (ChatRoomUserDto) member;
                    if (m.getUserId().equals(userId)) {
                        redisTemplate.opsForSet().remove(PARTY_PREFIX + dto.getPartyId(), member);
                        break;
                    }
                }
            }
        }
    }

    // ✅ 특정 파티 멤버 전체 조회
    public Set<Object> getPartyMembers(Long partyId) {
        return redisTemplate.opsForSet().members(PARTY_PREFIX + partyId);
    }

    // ✅ userId 기준으로 참여 중인 파티 ID 조회
    public Long getPartyIdByUser(Long userId) {
        ChatRoomUserDto dto = (ChatRoomUserDto) redisTemplate.opsForValue().get(USER_PREFIX + userId);
        return dto != null ? dto.getPartyId() : null;
    }

    // ✅ partyId + userId로 특정 멤버 조회
    public ChatRoomUserDto getPartyMemberByPartyIdAndUserId(Long partyId, Long userId) {
        Set<Object> members = redisTemplate.opsForSet().members(PARTY_PREFIX + partyId);
        if (members != null) {
            for (Object member : members) {
                ChatRoomUserDto dto = (ChatRoomUserDto) member;
                if (dto.getUserId().equals(userId)) {
                    return dto;
                }
            }
        }
        return null;
    }
}
