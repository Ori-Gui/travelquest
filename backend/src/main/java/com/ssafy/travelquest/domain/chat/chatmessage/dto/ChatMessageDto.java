package com.ssafy.travelquest.domain.chat.chatmessage.dto;

import com.ssafy.travelquest.domain.user.entity.JobCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDto {
    private Long chatRoomId;
    private Long userId;
    private String name;
    private JobCode job;
    private String message;
    private String sentAt;
}
