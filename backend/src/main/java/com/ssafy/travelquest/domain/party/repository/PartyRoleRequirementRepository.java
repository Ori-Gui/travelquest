package com.ssafy.travelquest.domain.party.repository;

import com.ssafy.travelquest.domain.party.entity.PartyRoleRequirement;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PartyRoleRequirementRepository {
    int insert(PartyRoleRequirement partyRoleRequirement);
    int insertList(List<PartyRoleRequirement> partyRoleRequirement);

    void deleteByPartyId(Long partyId);

    void deleteByJobCodeId(Long jobCodeId);

    List<PartyRoleRequirement> findByPartyId(Long partyId);
}
