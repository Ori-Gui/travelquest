package com.ssafy.travelquest.domain.chat.chatmessage.controller;

import com.ssafy.travelquest.domain.chat.chatmessage.dto.ChatMessageDto;
import com.ssafy.travelquest.domain.chat.chatmessage.service.ChatMessageService;
import com.ssafy.travelquest.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatMessageRestController {

    private final ChatMessageService chatMessageService;

    @GetMapping("/{chatRoomId}/messages")
    public List<ChatMessageDto> getMessages(
            @PathVariable Long chatRoomId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime before,
            @RequestParam(defaultValue = "50") int limit,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long userId = user.getId();
        return chatMessageService.getMessages(chatRoomId, userId, before, limit);
    }
}
