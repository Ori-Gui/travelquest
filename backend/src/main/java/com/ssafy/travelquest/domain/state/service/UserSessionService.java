package com.ssafy.travelquest.domain.state.service;

import com.ssafy.travelquest.domain.state.dao.RedisUserSessionDao;
import com.ssafy.travelquest.domain.state.dto.ChatRoomUserDto;
import com.ssafy.travelquest.domain.state.dto.PartyMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserSessionService {

    private final RedisUserSessionDao redisDao;

    public void onConnect(String userId, ChatRoomUserDto dto) {
        redisDao.saveUserParty(Long.parseLong(userId), dto);
    }

    public void onDisconnect(Long userId) {
        redisDao.removeUserParty(userId);
    }

    public List<ChatRoomUserDto> getMembers(String partyId) {
        Set<Object> result = redisDao.getPartyMembers(Long.parseLong(partyId));
        return result.stream()
                .map(o -> (ChatRoomUserDto) o)
                .toList();
    }

    public Long getParty(String userId) {
        return redisDao.getPartyIdByUser(Long.parseLong(userId));
    }

    public ChatRoomUserDto getPartyMemberByPartyIdAndUserId(Long partyId, Long userId) {
        return redisDao.getPartyMemberByPartyIdAndUserId(partyId, userId);
    }
}

