package com.ssafy.travelquest.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MbtiAnswer {
    private String type;
    private int value;
}
