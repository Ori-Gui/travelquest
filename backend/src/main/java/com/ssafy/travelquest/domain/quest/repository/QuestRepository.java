package com.ssafy.travelquest.domain.quest.repository;

import org.apache.ibatis.annotations.Mapper;
import com.ssafy.travelquest.domain.quest.entity.Quest;

@Mapper
public interface QuestRepository {
    void insertQuest(Quest quest);
}