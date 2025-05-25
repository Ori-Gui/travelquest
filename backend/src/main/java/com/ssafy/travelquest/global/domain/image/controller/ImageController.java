package com.ssafy.travelquest.global.domain.image.controller;

import com.ssafy.travelquest.global.domain.image.dto.ImageUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.ssafy.travelquest.global.domain.image.service.ImageService;
import java.net.URI;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * Controller for handling image upload, download, and deletion.
 */
@RestController
@RequestMapping("/api/v1/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<ImageUploadResponse> upload(
            @RequestParam("file") MultipartFile file) {

        // 1) 파일 저장
        String filename = imageService.store(file);

        // 2) Location 헤더용 URI 생성 (path variable로 넘기고, encode())
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/image/{filename}")
                .buildAndExpand(filename)  // filename을 path 변수가로 바인딩
                .encode()                  // percent-encode (한글, 공백 등을 %XX로)
                .toUri();

        // 3) 응답 바디 DTO
        ImageUploadResponse response = ImageUploadResponse.builder()
                .filename(filename)
                .url(location.toString()) // 이미 percent-encoded 된 풀 URL
                .build();

        return ResponseEntity
                .created(location)
                .body(response);
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> download(@PathVariable String filename) {
        Resource resource = imageService.load(filename);

        // 1) Content-Type 결정
        String contentType = URLConnection.guessContentTypeFromName(resource.getFilename());
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        // 2) Content-Disposition 헤더를 스프링에서 안전하게 생성
        ContentDisposition contentDisposition = ContentDisposition
                .inline()
                .filename(resource.getFilename(), StandardCharsets.UTF_8)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDisposition(contentDisposition);

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    @DeleteMapping("/{filename:.+}")
    public ResponseEntity<Void> delete(@PathVariable String filename) {
        imageService.delete(filename);
        return ResponseEntity.noContent().build();
    }
}
