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
    private JobCode job;

    public static ChatUserDto of(Long userId, String name, JobCode job) {
        return ChatUserDto.builder()
                .userId(userId)
                .name(name)
                .job(job)
                .build();
    }
}
