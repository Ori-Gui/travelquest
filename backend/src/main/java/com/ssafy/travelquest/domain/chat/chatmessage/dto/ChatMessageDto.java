package com.ssafy.travelquest.domain.chat.chatmessage.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDto {

    private Long chatRoomId;
    private Long userId;
    private String message;
    private String sentAt;
}

