package com.ssafy.travelquest.domain.party.repository;

import com.ssafy.travelquest.domain.party.entity.PartyMember;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PartyMemberRepository {
    int insertPartyMember(PartyMember partyMember);

    int deletePartyMember(Long partyId, Long userId);

    int deletePartyMemberByUserId(Long userId);

    List<PartyMember> getPartyMembersByPartyId(Long partyId);
}
