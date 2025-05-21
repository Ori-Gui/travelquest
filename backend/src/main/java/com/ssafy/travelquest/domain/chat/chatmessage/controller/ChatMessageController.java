package com.ssafy.travelquest.domain.chat.chatmessage.controller;

import com.ssafy.travelquest.domain.chat.chatmessage.dto.ChatMessageDto;
import com.ssafy.travelquest.domain.chat.chatmessage.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat.send.{chatRoomId}")
    @SendTo("/topic/chat/{chatRoomId}")
    public ChatMessageDto sendMessage(
            @DestinationVariable Long chatRoomId,
            ChatMessageDto message
    ) {
        log.info("Received message: {}", message);
        chatMessageService.saveChatMessage(message, chatRoomId);
        return message;
    }
}
