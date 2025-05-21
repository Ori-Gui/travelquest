package com.ssafy.travelquest.domain.party.controller;

import com.ssafy.travelquest.domain.party.dto.PartyMemberDetailResponse;
import com.ssafy.travelquest.domain.party.service.PartyMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/partymember")
public class PartyMemberController {
    private final PartyMemberService partyMemberService;

    @GetMapping("/party/{partyId}")
    public ResponseEntity<List<PartyMemberDetailResponse>> getPartyMembersByPartyId(@PathVariable Long partyId) {
        List<PartyMemberDetailResponse> partyMembers = partyMemberService.getPartyMembersByPartyId(partyId);
        return ResponseEntity.ok(partyMembers);
    }
}
