package com.ssafy.travelquest.domain.user.entity;

import com.ssafy.travelquest.domain.user.dto.UserProfileEditRequest;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private Long id;
    private String userName;
    private String email;
    private MBTI mbti;
    private JobCode jobClassCode;
    private UserRole role;
    private RegistStatus registStatus;
    private LocalDate birthday;
    private LocalDateTime createdAt;

    public static User of() {
        return User.builder()
                .userName("익명")
                .mbti(MBTI.NONE)
                .jobClassCode(JobCode.NONE)
                .role(UserRole.USER)
                .registStatus(RegistStatus.NOT_YET)
                .birthday(null)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static User update(User user, UserProfileEditRequest request) {
        return user.toBuilder()
                .userName(request.userName())
                .email(request.email())
                .birthday(request.birthday())
                .registStatus(RegistStatus.IN_PROGRESS)
                .build();
    }

    public static User updateMbti(User user, MBTI mbti) {
        return user.toBuilder()
                .mbti(mbti)
                .registStatus(RegistStatus.REGISTERED)
                .build();
    }
}
