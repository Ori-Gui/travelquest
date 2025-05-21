package com.ssafy.travelquest.domain.chat.chatroom.service;

import com.ssafy.travelquest.domain.chat.chatroom.dto.ChatRoomResponse;
import com.ssafy.travelquest.domain.chat.chatroom.entity.ChatRoom;
import com.ssafy.travelquest.domain.chat.chatroom.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public ChatRoomResponse joinChatRoom(Long partyId) {
        Optional<ChatRoom> existingChatRoom = chatRoomRepository.findByPartyId(partyId);
        if(existingChatRoom.isPresent()) {
            return ChatRoomResponse.builder()
                    .chatRoomId(existingChatRoom.get().getId())
                    .build();
        } else {
            ChatRoom newChatRoom = ChatRoom.of(partyId);
            chatRoomRepository.insert(newChatRoom);
            return ChatRoomResponse.builder()
                    .chatRoomId(newChatRoom.getId())
                    .build();

        }
    }
}
