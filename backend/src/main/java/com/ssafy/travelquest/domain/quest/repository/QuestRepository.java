package com.ssafy.travelquest.domain.quest.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.travelquest.domain.quest.entity.Quest;

@Mapper
public interface QuestRepository {
    List<Quest> findByDungeonId(Integer dungeonId);
    void insertQuest(Quest quest);
}
