package com.ssafy.travelquest.domain.party.repository;

import com.ssafy.travelquest.domain.party.dto.PartyInfoResponse;
import com.ssafy.travelquest.domain.party.entity.Party;
import com.ssafy.travelquest.domain.party.entity.PartyStatus;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface PartyRepository {
    int insert(Party party);
    Party findById(Long id);
    void update(Party party);
    void delete(Long id);

    List<Party> findByDungeonId(Long dungeonId);
    List<Party> findByUserId(Long userId);

    List<PartyInfoResponse> findPartyDetailsByDungeonId(Long dungeonId);
    List<Party> findByIds(List<Long> partyIds);
    void updateStatus(
            @Param("partyId") Long partyId,
            @Param("status") PartyStatus status
    );
}
