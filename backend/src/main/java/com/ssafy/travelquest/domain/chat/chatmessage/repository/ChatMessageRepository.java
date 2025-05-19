package com.ssafy.travelquest.domain.chat.chatmessage.repository;

import com.ssafy.travelquest.domain.chat.chatmessage.entity.ChatMessageDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessageDocument, String> {
    List<ChatMessageDocument> findByPartyIdOrderBySentAt(Long partyId);
}
