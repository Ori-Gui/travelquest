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

    @MessageMapping("/chat.send.{partyId}")
    @SendTo("/topic/chat/{partyId}")
    public ChatMessageDto sendMessage(
            @DestinationVariable Long partyId,
            ChatMessageDto message
    ) {
        log.info("Received message: {}", message);
        chatMessageService.saveChatMessage(message, partyId);
        return message;
    }
}
