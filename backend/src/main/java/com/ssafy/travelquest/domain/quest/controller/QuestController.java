package com.ssafy.travelquest.domain.quest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ssafy.travelquest.domain.quest.entity.Quest;
import com.ssafy.travelquest.domain.quest.service.QuestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/dungeons/{dungeonId}/quests")
@RequiredArgsConstructor
@Validated
public class QuestController {

    private final QuestService questService;

    /** 던전 ID에 속한 퀘스트 목록 조회 */
    @GetMapping
    public ResponseEntity<List<Quest>> getQuestsByDungeonId(
            @PathVariable Integer dungeonId) {
        List<Quest> quests = questService.getQuestsByDungeonId(dungeonId);
        if (quests.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(quests);
    }
}
