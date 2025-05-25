package com.ssafy.travelquest.global.domain.image.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final Path rootLocation = Paths.get("uploads/images");

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage directory", e);
        }
    }

    public String save(MultipartFile file) {
        String original = Objects.requireNonNull(file.getOriginalFilename());
        String filename = System.currentTimeMillis() + "_" + StringUtils.cleanPath(original);
        try {
            Path destination = rootLocation.resolve(filename);
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + filename, e);
        }
    }

    public Resource load(String path) {
        try {
            Path file = rootLocation.resolve(path).normalize();
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read file: " + path);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + path, e);
        }
    }

    public void delete(String path) {
        try {
            Path file = rootLocation.resolve(path).normalize();
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file: " + path, e);
        }
    }
}
