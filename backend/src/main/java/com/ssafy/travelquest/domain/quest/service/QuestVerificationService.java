package com.ssafy.travelquest.domain.quest.service;

import com.ssafy.travelquest.domain.quest.entity.QuestVerification;
import com.ssafy.travelquest.domain.quest.entity.QuestVerificationStatus;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.ssafy.travelquest.domain.quest.repository.QuestVerificationRepository;

@Service
@RequiredArgsConstructor
public class QuestVerificationService {
    private final QuestVerificationRepository questVerificationRepository;

    public void createVerification(QuestVerification verification) {
        QuestVerification toSave = verification.toBuilder()
            .status(QuestVerificationStatus.PENDING) 
            .verifiedAt(LocalDateTime.now())
            .build();

        questVerificationRepository.insertQuestVerification(toSave);
    }
}

