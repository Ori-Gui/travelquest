package com.ssafy.travelquest.domain.quest.repository;

import org.apache.ibatis.annotations.Mapper;
import com.ssafy.travelquest.domain.quest.entity.QuestVerification;
import com.ssafy.travelquest.domain.quest.entity.QuestVerificationStatus;

import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface QuestVerificationRepository {
    int insertQuestVerification(QuestVerification verification);
    List<QuestVerification> selectVerificationsByParty(@Param("partyId") Integer partyId);
    Optional<QuestVerification> findById(@Param("id") Integer id);
    void deleteById(@Param("id") Integer id);
    void updateStatus(
            @Param("id") Integer id,
            @Param("status") QuestVerificationStatus status
        );
}