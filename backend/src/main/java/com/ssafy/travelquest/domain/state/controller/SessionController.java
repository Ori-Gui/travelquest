package com.ssafy.travelquest.domain.state.controller;

import com.ssafy.travelquest.domain.state.dto.PartyMemberResponse;
import com.ssafy.travelquest.domain.state.service.UserSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/session")
public class SessionController {
    private final UserSessionService userSessionService;

    @GetMapping("/party/member/{partyId}")
    public ResponseEntity<List<PartyMemberResponse>> getPartyMembers(@PathVariable String partyId) {
        log.debug("partyId: {}", partyId);
        List<PartyMemberResponse> members = userSessionService.getMembers(partyId);
        return ResponseEntity.ok(members);
    }

}
