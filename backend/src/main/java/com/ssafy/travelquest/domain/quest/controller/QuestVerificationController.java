package com.ssafy.travelquest.domain.quest.controller;

import com.ssafy.travelquest.domain.quest.dto.QuestVerificationInfoDto;
import com.ssafy.travelquest.domain.quest.dto.QuestVerificationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;
import com.ssafy.travelquest.domain.quest.entity.QuestVerification;
import com.ssafy.travelquest.domain.quest.service.QuestVerificationService;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/quest-verifications")
@RequiredArgsConstructor
public class QuestVerificationController {
    private final QuestVerificationService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<QuestVerificationResponse> submitVerification(
            @RequestParam Integer questId,
            @RequestParam Integer partyId,
            @RequestParam Long verifiedByUserId,
            @RequestParam("photo") MultipartFile photo) {

        if (photo.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        String photoUrl = service.store(photo);

        QuestVerification saved = service.createVerification(
                QuestVerification.builder()
                        .questId(questId)
                        .partyId(partyId)
                        .verifiedByUserId(verifiedByUserId)
                        .photoUrl(photoUrl)
                        .build()
        );

        // 응답 DTO
        QuestVerificationResponse body = new QuestVerificationResponse(
                saved.getId(), saved.getPhotoUrl(),
                saved.getStatus().name(), saved.getVerifiedAt()
        );

        URI location = URI.create("/api/v1/quest-verifications/" + saved.getId());
        return ResponseEntity.created(location).body(body);
    }

    @GetMapping
    public ResponseEntity<List<QuestVerificationInfoDto>> getVerifications(
            @RequestParam Integer partyId
    ) {
        List<QuestVerificationInfoDto> infos = service.getVerificationsByParty(partyId);
        return ResponseEntity.ok(infos);
    }
    
    @DeleteMapping("/{verificationId}")
    public ResponseEntity<Void> deleteVerification(@PathVariable Integer verificationId) {
    	service.deleteVerification(verificationId);
        return ResponseEntity.ok().build();
    }
    
    @PatchMapping("/{verificationId}")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Integer verificationId,
            @RequestBody Map<String, String> body   // 또는 별도 DTO
    ) {
        String status = body.get("status");
        service.updateVerificationStatus(verificationId, status);
        return ResponseEntity.ok().build();
    }
}