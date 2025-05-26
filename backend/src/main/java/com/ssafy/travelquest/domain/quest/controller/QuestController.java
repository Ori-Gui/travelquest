package com.ssafy.travelquest.domain.quest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ssafy.travelquest.domain.quest.entity.Quest;
import com.ssafy.travelquest.domain.quest.service.QuestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class QuestController {

    private final QuestService questService;

    /** 던전 ID에 속한 퀘스트 목록 조회 */
    @GetMapping("/dungeons/{dungeonId}/quests")
    public ResponseEntity<List<Quest>> getQuestsByDungeonId(
            @PathVariable Integer dungeonId,
            @RequestParam(required = false) Long partyId
    ) {
        List<Quest> quests = questService.getQuestsByDungeonId(dungeonId, partyId);
        if (quests.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(quests);
    }
    
    @GetMapping("/quests/{questId}")
    public ResponseEntity<Quest> getQuestById(@PathVariable Integer questId) {
        Quest quest = questService.getQuestById(questId);
        return ResponseEntity.ok(quest);
    }
}
