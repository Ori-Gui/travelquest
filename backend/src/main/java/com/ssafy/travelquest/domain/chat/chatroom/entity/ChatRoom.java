package com.ssafy.travelquest.domain.chat.chatroom.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class ChatRoom {
    private Long id;
    private Long partyId;
    private LocalDateTime createdAt;

    public static ChatRoom of(Long partyId) {
        return ChatRoom.builder()
                .partyId(partyId)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
