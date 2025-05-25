package com.ssafy.travelquest.domain.quest.repository;

import org.apache.ibatis.annotations.Mapper;
import com.ssafy.travelquest.domain.quest.entity.QuestVerification;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface QuestVerificationRepository {
    int insertQuestVerification(QuestVerification verification);
    List<QuestVerification> selectVerificationsByParty(@Param("partyId") Integer partyId);
}