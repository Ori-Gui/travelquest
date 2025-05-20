package com.ssafy.travelquest.domain.party.dto;

import com.ssafy.travelquest.domain.user.entity.JobCode;

public record PartyRoleRequireRequest(
        JobCode jobCode,
        Integer maxCount
) {
}
