package com.ssafy.travelquest.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobClass {
    private JobCode code;
    private String name;
    private String description;
    private String imageUrl;
    private String iconUrl;
}
