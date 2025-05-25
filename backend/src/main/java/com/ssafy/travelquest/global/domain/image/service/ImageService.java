package com.ssafy.travelquest.global.domain.image.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final StorageService storage;

    public String store(MultipartFile file) {
        validateFile(file);
        return storage.save(file);
    }

    public Resource load(String path) {
        return storage.load(path);
    }

    public void delete(String path) {
        storage.delete(path);
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어 있습니다.");
        }
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("이미지 파일만 업로드할 수 있습니다.");
        }
    }
}
