package com.ssafy.travelquest.domain.chat.chatmessage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SystemMessageDto {
    private String name;
    private Long userId;
    private String job;
    private boolean system;
    private String content;


}

