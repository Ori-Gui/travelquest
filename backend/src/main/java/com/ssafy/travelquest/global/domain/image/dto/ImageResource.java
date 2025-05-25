package com.ssafy.travelquest.global.domain.image.dto;

import lombok.Getter;
import org.springframework.core.io.Resource;

@Getter
public class ImageResource {
    private final Resource resource;
    private final String contentType;

    public ImageResource(Resource resource, String contentType) {
        this.resource = resource;
        this.contentType = contentType;
    }
}

