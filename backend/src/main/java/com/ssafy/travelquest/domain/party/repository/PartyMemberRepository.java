package com.ssafy.travelquest.domain.party.repository;

import com.ssafy.travelquest.domain.party.dto.PartyMemberDetailResponse;
import com.ssafy.travelquest.domain.party.dto.PartyMemberInfo;
import com.ssafy.travelquest.domain.party.entity.PartyMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PartyMemberRepository {
    int insertPartyMember(PartyMember partyMember);

    int deletePartyMember(Long partyId, Long userId);

    int deletePartyMemberByUserId(Long userId);

    List<PartyMember> getPartyMembersByPartyId(Long partyId);
    List<PartyMemberInfo> getPartyMemberInfoByPartyId(Long partyId);
    List<PartyMemberDetailResponse> findMembersByPartyId(@Param("partyId") Long partyId);

    Optional<PartyMember> findByUserAndParty(
            @Param("userId") Long userId,
            @Param("partyId") Long partyId
    );

    List<PartyMember> findByUserId(@Param("userId") Long userId);

}
