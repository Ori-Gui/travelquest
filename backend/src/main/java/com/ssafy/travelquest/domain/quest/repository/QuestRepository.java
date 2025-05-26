package com.ssafy.travelquest.domain.quest.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.travelquest.domain.quest.entity.Quest;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface QuestRepository {
    List<Quest> findByDungeonId(Integer dungeonId);
    void insertQuest(Quest quest);

    List<Quest> findCompletedDungeonQuestsByUserId(
            @Param("userId") long userId,
            @Param("offset") int offset,
            @Param("size") int size
    );
    Optional<Quest> findById(@Param("id") Integer id);
}
