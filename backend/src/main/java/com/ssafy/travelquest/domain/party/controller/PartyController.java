package com.ssafy.travelquest.domain.party.controller;

import com.ssafy.travelquest.domain.party.dto.CreatePartyRequest;
import com.ssafy.travelquest.domain.party.dto.PartyInfoResponse;
import com.ssafy.travelquest.domain.party.dto.PartyResponse;
import com.ssafy.travelquest.domain.party.dto.RequiredJobResponse;
import com.ssafy.travelquest.domain.party.service.PartyRoleRequirementService;
import com.ssafy.travelquest.domain.party.service.PartyService;
import com.ssafy.travelquest.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/party")
public class PartyController {
    private final PartyService partyService;
    private final PartyRoleRequirementService partyRoleRequirementService;

    @PostMapping("/{dungeonId}")
    public ResponseEntity<Void> createParty(@AuthenticationPrincipal CustomUserDetails userDetails
            , @RequestBody CreatePartyRequest request, @PathVariable Long dungeonId) {
        partyService.createParty(
                userDetails,
                request,
                dungeonId
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/join/{partyId}")
    public ResponseEntity<Void> joinParty(@AuthenticationPrincipal CustomUserDetails userDetails
            , @PathVariable Long partyId) {
        partyService.joinParty(
                userDetails,
                partyId
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{partyId}/info")
    public ResponseEntity<PartyResponse> getPartyInfo(@PathVariable Long partyId) {
        PartyResponse response = partyService.getPartyInfo(partyId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{dungeonId}")
    public ResponseEntity<List<PartyInfoResponse>> getPartyListByDungeonId(@PathVariable Long dungeonId) {
        List<PartyInfoResponse> partyResponses = partyService.getPartyDetailsByDungeonId(dungeonId);
        return ResponseEntity.ok(partyResponses);
    }

    @GetMapping("/{partyId}/required")
    public ResponseEntity<List<RequiredJobResponse>> getRequiredJobs(@PathVariable Long partyId) {
        List<RequiredJobResponse> requiredJobs = partyRoleRequirementService.getRequiredJobs(partyId);
        return ResponseEntity.ok(requiredJobs);
    }
}
