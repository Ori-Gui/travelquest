package com.ssafy.travelquest.domain.quest.controller;

import com.ssafy.travelquest.domain.quest.entity.Quest;
import com.ssafy.travelquest.domain.quest.service.QuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quest")
@RequiredArgsConstructor
public class QuestRestController {

    private final QuestService questService;

    @GetMapping("/my/{userId}")
    public ResponseEntity<List<Quest>> getCompletedDungeonQuestsByUserId(
            @PathVariable Long userId,
            @RequestParam int page,
            @RequestParam int size) {

        return ResponseEntity.ok(
                questService.getCompletedDungeonQuestsByUserId(userId, page, size)
        );
    }
}
