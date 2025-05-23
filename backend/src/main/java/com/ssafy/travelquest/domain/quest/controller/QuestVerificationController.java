package com.ssafy.travelquest.domain.quest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;
import com.ssafy.travelquest.domain.quest.entity.QuestVerification;
import com.ssafy.travelquest.domain.quest.service.QuestVerificationService;

@RestController
@RequestMapping("/api/v1/quest-verifications")
@RequiredArgsConstructor
public class QuestVerificationController {
    private final QuestVerificationService service;

    @PostMapping
    public ResponseEntity<Void> submitVerification(
            @RequestParam Integer questId,
            @RequestParam Integer partyId,
            @RequestParam Long verifiedByUserId,
            @RequestParam("photo") MultipartFile photo) {

        // TODO: 실제 파일 저장 로직 구현 후 photoUrl 생성
        String photoUrl = saveFile(photo);

        QuestVerification verification = QuestVerification.builder()
            .questId(questId)
            .partyId(partyId)
            .verifiedByUserId(verifiedByUserId)
            .photoUrl(photoUrl)
            .build();

        service.createVerification(verification);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private String saveFile(MultipartFile file) {
        // 예시: 파일 저장 후 접근 가능한 URL 반환
        return "/uploads/" + file.getOriginalFilename();
    }
}