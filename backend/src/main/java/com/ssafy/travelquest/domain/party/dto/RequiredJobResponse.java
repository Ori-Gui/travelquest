package com.ssafy.travelquest.domain.party.dto;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(toBuilder = true)
public class RequiredJobResponse {
    private String job;
    private String name;
    private List<String> mbtiList;
}

