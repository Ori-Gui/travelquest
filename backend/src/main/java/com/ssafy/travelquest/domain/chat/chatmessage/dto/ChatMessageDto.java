package com.ssafy.travelquest.domain.chat.chatmessage.dto;

import com.ssafy.travelquest.domain.chat.chatmessage.entity.ChatMessageDocument;
import com.ssafy.travelquest.domain.user.entity.JobCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ChatMessageDto {
    private Long chatRoomId;
    private Long userId;
    private String name;
    private String profileImage;
    private JobCode job;
    private String message;
    private String sentAt;

    public static ChatMessageDto from(ChatMessageDocument chatMessageDocument) {
        return ChatMessageDto.builder()
                .chatRoomId(chatMessageDocument.getChatRoomId())
                .userId(chatMessageDocument.getUserId())
                .message(chatMessageDocument.getMessage())
                .sentAt(chatMessageDocument.getSentAt().toString())
                .build();
    }

    public static ChatMessageDto from(ChatMessageDto chatMessageDto) {
        return ChatMessageDto.builder()
                .chatRoomId(chatMessageDto.getChatRoomId())
                .userId(chatMessageDto.getUserId())
                .name(chatMessageDto.getName())
                .profileImage(chatMessageDto.getProfileImage())
                .job(chatMessageDto.getJob())
                .message(chatMessageDto.getMessage())
                .sentAt(chatMessageDto.getSentAt())
                .build();
    }
}
