package com.ssafy.travelquest.domain.chat.chatmessage.controller;

import com.ssafy.travelquest.domain.chat.chatmessage.dto.ChatMessageDto;
import com.ssafy.travelquest.domain.chat.chatmessage.dto.ChatMessageSaveDto;
import com.ssafy.travelquest.domain.chat.chatmessage.dto.ChatUserDto;
import com.ssafy.travelquest.domain.chat.chatmessage.dto.SystemMessageDto;
import com.ssafy.travelquest.domain.chat.chatmessage.service.ChatMessageService;
import com.ssafy.travelquest.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send.{chatRoomId}")
    @SendTo("/topic/chat/{chatRoomId}")
    public ChatMessageDto sendMessage(
            @DestinationVariable Long chatRoomId,
            ChatMessageSaveDto message
    ) {
        log.info("Received message: {}", message);

        ChatUserDto profile = chatMessageService.userResolve(chatRoomId, message.getUserId()); // Redis → fallback DB

        ChatMessageDto messageWithProfile = ChatMessageDto.builder()
                .chatRoomId(chatRoomId)
                .userId(message.getUserId())
                .name(profile.getName())
                .job(profile.getJob())
                .message(message.getMessage())
                .sentAt(message.getSentAt())
                .build();


        chatMessageService.saveChatMessage(message, chatRoomId);
        return messageWithProfile;
    }

    @MessageMapping("/chat.enter.{partyId}")
    public void enterParty(
            @DestinationVariable Long partyId,
            SystemMessageDto msg
    ) {
        // (1) 만약 content 가 없다면 서버에서 채워주셔도 되고,
        // (2) 이미 프론트에서 채워 보냈다면 그대로 브로드캐스트
        if (msg.getContent() == null) {
            msg = msg.toBuilder()
                    .content(msg.getName() + "님이 파티에 들어왔습니다.")
                    .build();
        }
        messagingTemplate.convertAndSend(
                "/topic/chat/" + partyId,
                msg
        );
    }

}
