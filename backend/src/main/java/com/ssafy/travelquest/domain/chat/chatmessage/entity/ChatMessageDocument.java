package com.ssafy.travelquest.domain.chat.chatmessage.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chat_messages")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ChatMessageDocument {

    @Id
    private String id;
    private Long partyId;
    private Long userId;
    private String message;
    private String sentAt;

    public static ChatMessageDocument of(Long partyId, Long userId, String message, String sentAt) {
        return ChatMessageDocument.builder()
                .partyId(partyId)
                .userId(userId)
                .message(message)
                .sentAt(sentAt)
                .build();
    }
}
