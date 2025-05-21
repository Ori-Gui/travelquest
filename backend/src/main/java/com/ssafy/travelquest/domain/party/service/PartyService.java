package com.ssafy.travelquest.domain.party.service;

import com.ssafy.travelquest.domain.party.dto.CreatePartyRequest;
import com.ssafy.travelquest.domain.party.dto.PartyInfoResponse;
import com.ssafy.travelquest.domain.party.dto.PartyResponse;
import com.ssafy.travelquest.domain.party.entity.Party;
import com.ssafy.travelquest.domain.party.entity.PartyMember;
import com.ssafy.travelquest.domain.party.entity.PartyRoleRequirement;
import com.ssafy.travelquest.domain.party.repository.PartyMemberRepository;
import com.ssafy.travelquest.domain.party.repository.PartyRepository;
import com.ssafy.travelquest.domain.party.repository.PartyRoleRequirementRepository;
import com.ssafy.travelquest.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartyService {
    private final PartyRepository partyRepository;
    private final PartyRoleRequirementRepository partyRoleRequirementRepository;
    private final PartyMemberRepository partyMemberRepository;

    @Transactional
    public void createParty(CustomUserDetails userDetails, CreatePartyRequest request, Long dungeonId) {
        log.info("{}", request);
        Party party = Party.of(
                dungeonId,
                userDetails.getId(),
                request.title(),
                request.description(),
                request.maxMember()
        );

        partyRepository.insert(party);

        List<PartyRoleRequirement> partyRoleRequirements = request.partyRoleRequireRequests()
                .stream()
                .map(partyRoleRequireRequest -> PartyRoleRequirement.of(
                        party.getId(),
                        partyRoleRequireRequest.jobCode(),
                        partyRoleRequireRequest.maxCount()
                ))
                .toList();
        partyRoleRequirementRepository.insertList(partyRoleRequirements);

        partyMemberRepository.insertPartyMember(
                PartyMember.of(
                        party.getId(),
                        userDetails.getId(),
                        userDetails.getJobCode()
                )
        );
    }

    @Transactional
    public void joinParty(CustomUserDetails userDetails, Long partyId) {
        List<PartyMember> partyMembers = partyMemberRepository.getPartyMembersByPartyId(partyId);
        Party target = partyRepository.findById(partyId);
        List<PartyRoleRequirement> partyRoleRequirements = partyRoleRequirementRepository.findByPartyId(partyId);
        if (partyMembers.size() >= target.getMaxMember()) {
            throw new IllegalArgumentException("파티가 꽉찼습니다.");
        }
        for (PartyRoleRequirement requirement : partyRoleRequirements) {
            long currentCount = partyMembers.stream()
                    .filter(member -> member.getJobCode() == requirement.getJobCode())
                    .count();

            if (currentCount >= requirement.getMaxCount()) {
                throw new IllegalArgumentException("해당 직업군의 자리가 가득 찼습니다.");
            }
        }

        partyMemberRepository.insertPartyMember(
                PartyMember.of(
                        partyId,
                        userDetails.getId(),
                        userDetails.getJobCode()
                )
        );
    }

    @Transactional(readOnly = true)
    public List<PartyResponse> getPartyListByDungeonId(Long dungeonId) {
        List<Party> parties = partyRepository.findByDungeonId(dungeonId);
        return parties.stream()
                .map(party -> PartyResponse.of(
                        party.getId(),
                        party.getTitle(),
                        party.getDescription(),
                        party.getStatus(),
                        party.getMaxMember()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public PartyResponse getPartyInfo(Long partyId) {
        Party party = partyRepository.findById(partyId);
        return PartyResponse.of(
                party.getId(),
                party.getTitle(),
                party.getDescription(),
                party.getStatus(),
                party.getMaxMember()
        );
    }

    @Transactional(readOnly = true)
    public List<PartyInfoResponse> getPartyDetailsByDungeonId(Long dungeonId) {
        return partyRepository.findPartyDetailsByDungeonId(dungeonId);
    }
}
