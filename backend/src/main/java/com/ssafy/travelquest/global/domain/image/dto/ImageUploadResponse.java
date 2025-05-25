package com.ssafy.travelquest.global.domain.image.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageUploadResponse {
    private String filename;
    private String url;
}
