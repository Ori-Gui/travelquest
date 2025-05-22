package com.ssafy.travelquest.domain.party.service;

import com.ssafy.travelquest.domain.party.dto.PartyMemberDetailResponse;
import com.ssafy.travelquest.domain.party.repository.PartyMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartyMemberService {

    private final PartyMemberRepository partyMemberRepository;

    @Transactional(readOnly = true)
    public List<PartyMemberDetailResponse> getPartyMembersByPartyId(Long partyId) {
        return partyMemberRepository.findMembersByPartyId(partyId);
    }
}
