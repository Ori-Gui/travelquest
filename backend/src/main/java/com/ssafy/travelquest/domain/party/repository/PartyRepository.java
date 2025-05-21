package com.ssafy.travelquest.domain.party.repository;

import com.ssafy.travelquest.domain.party.dto.PartyInfoResponse;
import com.ssafy.travelquest.domain.party.entity.Party;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PartyRepository {
    int insert(Party party);
    Party findById(Long id);
    void update(Party party);
    void delete(Long id);

    List<Party> findByDungeonId(Long dungeonId);

    List<PartyInfoResponse> findPartyDetailsByDungeonId(Long dungeonId);
}
