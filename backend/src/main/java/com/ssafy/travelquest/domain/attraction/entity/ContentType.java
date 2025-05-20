package com.ssafy.travelquest.domain.attraction.entity;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ContentType {
    private Integer contentTypeId;
    private String contentTypeName;
}
