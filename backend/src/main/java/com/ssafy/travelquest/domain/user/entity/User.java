package com.ssafy.travelquest.domain.user.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String userName;
    private String email;
    private MBTI mbti;
    private JobCode jobClassCode;
    private LocalDateTime joinDate;
    private UserRole role;

    public String getFormattedJoinDate() {
        return (joinDate != null) ? joinDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "";
    }

    public static User of() {
        return User.builder()
                .userName("익명")
                .mbti(MBTI.NONE)
                .jobClassCode(JobCode.NONE)
                .joinDate(LocalDateTime.now())
                .role(UserRole.USER)
                .build();
    }
}
