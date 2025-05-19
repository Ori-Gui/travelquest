package com.ssafy.travelquest.domain.state.service;

import com.ssafy.travelquest.domain.state.dao.RedisUserSessionDao;
import com.ssafy.travelquest.domain.state.dto.PartyMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserSessionService {

    private final RedisUserSessionDao redisDao;

    public void onConnect(String userId, String partyId) {
        redisDao.saveUserParty(userId, partyId);
    }

    public void onDisconnect(String userId) {
        redisDao.removeUserParty(userId);
    }

    public List<PartyMemberResponse> getMembers(String partyId) {
        Set<Object> result = redisDao.getPartyMembers(partyId);
        return result.stream()
                .map(userId -> new PartyMemberResponse(Long.parseLong((String) userId)))
                .toList();
    }

    public String getParty(String userId) {
        return redisDao.getPartyByUser(userId);
    }
}

