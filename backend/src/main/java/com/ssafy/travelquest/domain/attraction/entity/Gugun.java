package com.ssafy.travelquest.domain.attraction.entity;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Gugun {
    private Integer no;
    private Integer sidoCode;
    private Integer gugunCode;
    private String gugunName;
}