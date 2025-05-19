package com.ssafy.travelquest.domain.chat.chatmessage.service;

import com.ssafy.travelquest.domain.chat.chatmessage.dto.ChatMessageDto;
import com.ssafy.travelquest.domain.chat.chatmessage.entity.ChatMessageDocument;
import com.ssafy.travelquest.domain.chat.chatmessage.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public void saveChatMessage(ChatMessageDto message, Long partyId) {
        ChatMessageDocument chatMessageDocument = ChatMessageDocument.of(
                partyId,
                message.getUserId(),
                message.getMessage(),
                message.getSentAt()
        );

        chatMessageRepository.save(chatMessageDocument);
    }
}
