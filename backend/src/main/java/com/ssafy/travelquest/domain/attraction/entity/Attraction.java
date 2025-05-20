package com.ssafy.travelquest.domain.attraction.entity;

import java.math.BigDecimal;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Attraction {
    private Integer no;
    private Integer contentId;
    private String title;
    private Integer contentTypeId;
    private Integer areaCode;
    private Integer siGunGuCode;
    private String firstImage1;
    private String firstImage2;
    private Integer mapLevel;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String tel;
    private String addr1;
    private String addr2;
    private String homepage;
    private String overview;
}