package com.ssafy.travelquest.domain.attraction.entity;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Sido {
    private Integer no;
    private Integer sidoCode;
    private String sidoName;
}