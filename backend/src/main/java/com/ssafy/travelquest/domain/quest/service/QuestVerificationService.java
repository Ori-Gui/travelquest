package com.ssafy.travelquest.domain.quest.service;

import com.ssafy.travelquest.domain.quest.dto.QuestVerificationInfoDto;
import com.ssafy.travelquest.domain.quest.entity.QuestVerification;
import com.ssafy.travelquest.domain.quest.entity.QuestVerificationStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.ssafy.travelquest.domain.quest.repository.QuestVerificationRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestVerificationService {
    private final QuestVerificationRepository repo;

    @Value("${upload.dir}")
    private String uploadDir;

    public String store(MultipartFile file) {
        String original = StringUtils.cleanPath(file.getOriginalFilename());
        String ext = original.contains(".")
                ? original.substring(original.lastIndexOf('.'))
                : "";
        String filename = UUID.randomUUID() + ext;
        log.info("▶▶ store() called, uploadDir=[{}], filename=[{}]", uploadDir, filename);
        Path folder = Paths.get(uploadDir).toAbsolutePath().normalize();
        log.info("▶▶ normalized folder path: {}", folder);

        try {
            Files.createDirectories(folder);
            Path target = folder.resolve(filename);
            try (InputStream in = file.getInputStream()) {
                Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
            }
            log.info("▶▶ file saved to {}", target);
            return "/images/" + filename;
        } catch (IOException e) {
            log.error("파일 저장 실패", e);
            throw new RuntimeException("파일 저장 실패", e);
        }
    }

    public QuestVerification createVerification(QuestVerification verification) {
        QuestVerification toSave = verification.toBuilder()
                .status(QuestVerificationStatus.PENDING)
                .verifiedAt(LocalDateTime.now())
                .build();
        repo.insertQuestVerification(toSave);
        return toSave;
    }

    public List<QuestVerificationInfoDto> getVerificationsByParty(Integer partyId) {
        List<QuestVerification> list = repo.selectVerificationsByParty(partyId);
        return list.stream()
                .map(v -> new QuestVerificationInfoDto(
                        v.getId(),
                        v.getQuestId(),
                        v.getPartyId(),
                        v.getVerifiedByUserId(),
                        v.getPhotoUrl(),
                        v.getStatus().name(),
                        v.getVerifiedAt()
                ))
                .collect(Collectors.toList());
    }
    
    public void deleteVerification(Integer id) {
        // 1) 존재 여부 체크(optional)
        repo.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Verification not found: " + id));
        // 2) 삭제
        repo.deleteById(id);
    }
    
    public void updateVerificationStatus(Integer id, String statusStr) {
        QuestVerificationStatus status = QuestVerificationStatus.valueOf(statusStr);
        // 1) 존재 확인(optional)
        repo.findById(id)
          .orElseThrow(() -> new NoSuchElementException("검증 글 없음: " + id));
        // 2) 상태 변경
        repo.updateStatus(id, status);
    }
}

