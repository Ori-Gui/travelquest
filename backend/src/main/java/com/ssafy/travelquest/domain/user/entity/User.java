package com.ssafy.travelquest.domain.user.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Integer no;
    private String userId;
    private String userName;
    private String password;
    private String email;
    private LocalDateTime joinDate;
    private UserRole role;
    
    public String getFormattedJoinDate() {
        return (joinDate != null) ? joinDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "";
    }
    
    public static User of() {
        return User.builder()
            .userId("anonymous")
            .userName("익명")
            .password("default_pw")
            .email("default@example.com")
            .joinDate(LocalDateTime.now())
            .role(UserRole.USER)
            .build();
    }

}



