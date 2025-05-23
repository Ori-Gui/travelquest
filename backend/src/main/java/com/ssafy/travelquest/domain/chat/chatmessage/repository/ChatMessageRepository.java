package com.ssafy.travelquest.domain.chat.chatmessage.repository;

import com.ssafy.travelquest.domain.chat.chatmessage.entity.ChatMessageDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessageDocument, String> {
    List<ChatMessageDocument> findByChatRoomIdOrderBySentAt(Long chatRoomId);

    Page<ChatMessageDocument> findByChatRoomIdAndSentAtBetweenOrderBySentAtDesc(
            Long chatRoomId,
            LocalDateTime joinedAt,
            LocalDateTime before,
            Pageable pageable
    );

    @Query("{ 'chatRoomId': ?0, 'sentAt': { $gte: ?1, $lte: ?2 } }")
    Page<ChatMessageDocument> findMessagesBetween(
            Long chatRoomId,
            String joinedAt,
            String before,
            Pageable pageable
    );

    // 채팅방 ID, joinedAt ≤ sentAt < before 범위에서 sentAt 오름차순으로 조회
    Page<ChatMessageDocument> findByChatRoomIdAndSentAtBetweenOrderBySentAtAsc(
            Long chatRoomId,
            LocalDateTime joinedAt,
            LocalDateTime before,
            Pageable pageable
    );
}
