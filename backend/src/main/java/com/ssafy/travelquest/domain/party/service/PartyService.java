package com.ssafy.travelquest.domain.party.service;

import com.ssafy.travelquest.domain.party.dto.CreatePartyRequest;
import com.ssafy.travelquest.domain.party.dto.PartyInfoResponse;
import com.ssafy.travelquest.domain.party.dto.PartyResponse;
import com.ssafy.travelquest.domain.party.entity.Party;
import com.ssafy.travelquest.domain.party.entity.PartyMember;
import com.ssafy.travelquest.domain.party.entity.PartyRoleRequirement;
import com.ssafy.travelquest.domain.party.entity.PartyStatus;
import com.ssafy.travelquest.domain.party.repository.PartyMemberRepository;
import com.ssafy.travelquest.domain.party.repository.PartyRepository;
import com.ssafy.travelquest.domain.party.repository.PartyRoleRequirementRepository;
import com.ssafy.travelquest.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        for(PartyMember partyMember : partyMembers) {
        	if(partyMember.getUserId() == userDetails.getId()) {
        		throw new IllegalArgumentException("이미 참가되어 있는 사용자입니다.");
        	}
        }
        for (PartyRoleRequirement requirement : partyRoleRequirements) {
        	if(!requirement.getJobCode().equals(userDetails.getJobCode())) continue;
            long currentCount = partyMembers.stream()
                    .filter(member -> member.getJobCode().equals(requirement.getJobCode()))
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
                        party.getDungeonId(),
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
                party.getDungeonId(),
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

    @Transactional
    public void leaveParty(CustomUserDetails userDetails, Long partyId) {
        Party party = partyRepository.findById(partyId);
        if (party == null) {
            throw new IllegalArgumentException("Party not found");
        }

        List<PartyMember> partyMembers = partyMemberRepository.getPartyMembersByPartyId(partyId);
        boolean isMember = false;
        for (PartyMember member : partyMembers) {
            if (member.getUserId().equals(userDetails.getId())) {
                isMember = true;
                break;
            }
        }

        if (!isMember) {
            throw new IllegalArgumentException("You are not a member of this party");
        }

        partyMemberRepository.deletePartyMember(partyId, userDetails.getId());
    }

    @Transactional
    public void kickUser(CustomUserDetails userDetails, Long partyId, Long targetUserId) {
        // 1. 요청자 정보 가져오기
        Long requesterId = userDetails.getId();

        // 2. 파티 존재 여부 확인
        Party party = partyRepository.findById(partyId);
        if (party == null) {
            throw new IllegalArgumentException("해당 파티를 찾을 수 없습니다.");
        }

        // 3. 권한 체크: 요청자가 파티 리더인지 확인
        if (!party.getLeaderId().equals(requesterId)) {
            throw new AccessDeniedException("파티 리더만 강퇴할 수 있습니다.");
        }

        // 4. 자기 자신 강퇴 방지
        if (targetUserId.equals(requesterId)) {
            throw new IllegalArgumentException("파티 리더는 스스로 강퇴할 수 없습니다.");
        }

        // 5. 대상 멤버 존재 여부 확인
        PartyMember member = partyMemberRepository.findByUserAndParty(targetUserId, partyId)
                .orElseThrow(() -> new IllegalArgumentException("강퇴 대상이 파티에 존재하지 않습니다."));
        if (member == null) {
            throw new IllegalArgumentException("강퇴 대상이 파티에 존재하지 않습니다.");
        }

        // 6. 강퇴 처리 (삭제 혹은 상태 업데이트)
        partyMemberRepository.deletePartyMember(partyId, targetUserId);
    }

    @Transactional(readOnly = true)
    public List<PartyResponse> getPartiesByUserId(Long userId) {
    	List<Party> result = partyRepository.findByUserId(userId);

        // TODO: Party → PartyResponse 변환 후 반환
        return result.stream()
                .map(PartyResponse::from) // PartyResponse.from(Party party) 정적 팩토리 메서드가 있다고 가정
                .collect(Collectors.toList());
    }

    @Transactional
    public void updatePartyStatus(Long partyId, String statusStr) {
        // enum 으로 변환해 주고
        PartyStatus newStatus = PartyStatus.valueOf(statusStr);
        // repository 로 SQL 실행
        partyRepository.updateStatus(partyId, newStatus);
    }
}
