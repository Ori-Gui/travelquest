package com.ssafy.travelquest.domain.chat.chatroom.controller;

import com.ssafy.travelquest.domain.chat.chatroom.dto.ChatRoomResponse;
import com.ssafy.travelquest.domain.chat.chatroom.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chatroom")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @PostMapping
    public ResponseEntity<ChatRoomResponse> createChatRoom(@RequestParam Long partyId) {
        return ResponseEntity.ok(
                chatRoomService.joinChatRoom(partyId)
        );
    }
}
