package com.ssafy.travelquest.global.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PartySessionInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            String partyId = accessor.getFirstNativeHeader("partyId");

            if (accessor.getSessionAttributes() != null) {
                accessor.getSessionAttributes().put("partyId", partyId);
            } else {
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("partyId", partyId);
                accessor.setSessionAttributes(attributes);
            }
            log.debug("partyId 세션에 저장 완료: {}", partyId);
        }

        return message;
    }

}
