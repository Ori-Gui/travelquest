package com.ssafy.travelquest.domain.quest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.travelquest.domain.quest.entity.Quest;
import com.ssafy.travelquest.domain.quest.repository.QuestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestService {

    private final QuestRepository questRepository;

    public List<Quest> getQuestsByDungeonId(Integer dungeonId) {
        return questRepository.findByDungeonId(dungeonId);
    }

    public List<Quest> getCompletedDungeonQuestsByUserId(long userId, int offset, int size) {
        return questRepository.findCompletedDungeonQuestsByUserId(userId, offset, size);
    }
}
