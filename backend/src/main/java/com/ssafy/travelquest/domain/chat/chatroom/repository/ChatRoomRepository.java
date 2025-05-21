package com.ssafy.travelquest.domain.chat.chatroom.repository;

import com.ssafy.travelquest.domain.chat.chatroom.entity.ChatRoom;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface ChatRoomRepository {
    Long insert(ChatRoom chatRoom);
    int deleteByPartyId(Long partyId);
    Optional<ChatRoom> findByPartyId(Long partyId);
    ChatRoom findById(Long id);
    void deleteById(Long id);
}
