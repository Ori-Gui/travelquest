package com.ssafy.travelquest.domain.chat.chatmessage.dto;

import com.ssafy.travelquest.domain.user.entity.JobCode;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatUserDto {
    private Long userId;
    private String name;
    private String profileImage;
    private JobCode job;

    public static ChatUserDto of(Long userId, String name, String profileImage, JobCode job) {
        return ChatUserDto.builder()
                .userId(userId)
                .name(name)
                .profileImage(profileImage) // Assuming profileImage is not provided in this context
                .job(job)
                .build();
    }
}
