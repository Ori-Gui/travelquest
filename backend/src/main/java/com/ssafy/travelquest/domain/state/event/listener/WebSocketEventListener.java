package com.ssafy.travelquest.domain.state.event.listener;

import com.ssafy.travelquest.domain.state.service.UserSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final UserSessionService userSessionService;

    @EventListener
    public void handleConnect(SessionConnectedEvent event) {
        String userId = event.getUser() != null ? event.getUser().getName() : null;

        Message<?> connectMessage = (Message<?>) event.getMessage().getHeaders().get("simpConnectMessage");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(connectMessage);

        String partyId = (String) accessor.getSessionAttributes().get("partyId");

        log.debug("WebSocket 연결됨: userId={}, partyId={}", userId, partyId);

        if (userId != null && partyId != null) {
            userSessionService.onConnect(userId, partyId);
        }
    }


    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        String userId = event.getUser() != null ? event.getUser().getName() : null;

        log.debug("WebSocket 연결 해제됨: userId={}", userId);
        if (userId != null) {
            userSessionService.onDisconnect(userId);
        }
    }

}

