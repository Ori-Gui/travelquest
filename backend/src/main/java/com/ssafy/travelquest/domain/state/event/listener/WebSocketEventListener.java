package com.ssafy.travelquest.domain.state.event.listener;

import com.ssafy.travelquest.domain.state.dto.ChatRoomUserDto;
import com.ssafy.travelquest.domain.state.service.UserSessionService;
import com.ssafy.travelquest.domain.user.entity.JobCode;
import com.ssafy.travelquest.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final UserSessionService userSessionService;

    @EventListener
    public void handleConnect(SessionConnectedEvent event) {

        Principal principal = event.getUser();
        if (principal instanceof CustomUserDetails customUser) {
            Long userId = customUser.getId();
            String name = customUser.getUsername(); // or getNickname() 등
            JobCode jobCode = customUser.getJobCode();

            Message<?> connectMessage = (Message<?>) event.getMessage().getHeaders().get("simpConnectMessage");
            StompHeaderAccessor accessor = StompHeaderAccessor.wrap(connectMessage);

            String partyId = (String) accessor.getSessionAttributes().get("partyId");

            log.debug("WebSocket 연결됨: userId={}, partyId={}", userId, partyId);

            if (userId != null && partyId != null) {
                userSessionService.onConnect(Long.toString(userId),
                        ChatRoomUserDto.of(userId, Long.parseLong(partyId), name, jobCode)
                );
            }
        }
    }


    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        Principal principal = event.getUser();

        if (principal instanceof CustomUserDetails customUser) {
            Long userId = customUser.getId(); // ✅ 정확한 userId 추출
            log.debug("WebSocket 연결 해제됨: userId={}", userId);
            userSessionService.onDisconnect(userId);
        } else {
            log.warn("Disconnected session with unknown principal: {}", principal);
        }
    }


}

