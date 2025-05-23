package com.ssafy.travelquest.domain.user.dto;

import com.ssafy.travelquest.domain.user.entity.JobCode;
import com.ssafy.travelquest.domain.user.entity.MBTI;
import com.ssafy.travelquest.domain.user.entity.RegistStatus;
import com.ssafy.travelquest.domain.user.entity.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
public class UserProfileResponse {
    private Long id;
    private String userName;
    private String email;
    private MBTI mbti;
    private JobCode jobClassCode;
    private UserRole role;
    private RegistStatus registStatus;
    private LocalDate birthday;
    private LocalDateTime createdAt;

    public static UserProfileResponse of(
            Long id,
            String userName,
            String email,
            MBTI mbti,
            JobCode jobClassCode,
            UserRole role,
            RegistStatus registStatus,
            LocalDate birthday,
            LocalDateTime createdAt
    ) {
        return UserProfileResponse.builder()
                .id(id)
                .userName(userName)
                .email(email)
                .mbti(mbti)
                .jobClassCode(jobClassCode)
                .role(role)
                .registStatus(registStatus)
                .birthday(birthday)
                .createdAt(createdAt)
                .build();
    }
}
