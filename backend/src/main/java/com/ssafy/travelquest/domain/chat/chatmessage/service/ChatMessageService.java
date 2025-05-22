package com.ssafy.travelquest.domain.chat.chatmessage.service;

import com.ssafy.travelquest.domain.chat.chatmessage.dto.ChatMessageSaveDto;
import com.ssafy.travelquest.domain.chat.chatmessage.dto.ChatUserDto;
import com.ssafy.travelquest.domain.chat.chatmessage.entity.ChatMessageDocument;
import com.ssafy.travelquest.domain.chat.chatmessage.repository.ChatMessageRepository;
import com.ssafy.travelquest.domain.state.dto.ChatRoomUserDto;
import com.ssafy.travelquest.domain.state.service.UserSessionService;
import com.ssafy.travelquest.domain.user.entity.User;
import com.ssafy.travelquest.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserService userService;
    private final UserSessionService userSessionService;

    public void saveChatMessage(ChatMessageSaveDto message, Long partyId) {
        ChatMessageDocument chatMessageDocument = ChatMessageDocument.of(
                partyId,
                message.getUserId(),
                message.getMessage(),
                message.getSentAt()
        );

        chatMessageRepository.save(chatMessageDocument);
    }

    public ChatUserDto userResolve(Long partyId, Long userId) {
        ChatRoomUserDto chatRoomUserDto = userSessionService.getPartyMemberByPartyIdAndUserId(partyId, userId);
        if (chatRoomUserDto != null) {
            return ChatUserDto.builder()
                    .name(chatRoomUserDto.getName())
                    .job(chatRoomUserDto.getJob())
                    .build();
        }

        User user = userService.getUser(userId);
        if (user == null) {
            return null;
        }

        return ChatUserDto.builder()
                .name(user.getUserName())
                .job(user.getJobClassCode())
                .build();
    }
}
