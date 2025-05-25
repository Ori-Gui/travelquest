package com.ssafy.travelquest.domain.state.dto;

import com.ssafy.travelquest.domain.user.entity.JobCode;
import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatRoomUserDto implements Serializable {
    private Long userId;
    private Long partyId;
    private String profileImage;
    private String name;
    private JobCode job;

    public static ChatRoomUserDto of(Long userId, Long partyId, String profileImage, String name, JobCode job) {
        return ChatRoomUserDto.builder()
                .userId(userId)
                .partyId(partyId)
                .profileImage(profileImage)
                .name(name)
                .job(job)
                .build();
    }
}
