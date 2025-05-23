package com.ssafy.travelquest.domain.quest.repository;

import org.apache.ibatis.annotations.Mapper;
import com.ssafy.travelquest.domain.quest.entity.QuestVerification;

@Mapper
public interface QuestVerificationRepository {
    int insertQuestVerification(QuestVerification verification);
}