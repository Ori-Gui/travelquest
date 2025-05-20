package com.ssafy.travelquest.domain.party.dto;

import java.util.List;

public record CreatePartyRequest(
        String title,
        Integer maxMember,
        List<PartyRoleRequireRequest> partyRoleRequireRequests
) {
}
