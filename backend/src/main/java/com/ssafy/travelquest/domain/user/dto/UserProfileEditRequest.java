package com.ssafy.travelquest.domain.user.dto;

import java.time.LocalDate;

public record UserProfileEditRequest(
        String userName,
        String email,
        String profileImageUrl,
        LocalDate birthday
) {
}
