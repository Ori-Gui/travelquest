// src/main/java/com/ssafy/travelquest/domain/dungeon/controller/DungeonController.java
package com.ssafy.travelquest.domain.dungeon.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ssafy.travelquest.domain.attraction.dto.AttractionSearchCondition;
import com.ssafy.travelquest.domain.attraction.entity.Attraction;
import com.ssafy.travelquest.domain.dungeon.entity.Dungeon;
import com.ssafy.travelquest.domain.dungeon.service.DungeonService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/dungeons")
@RequiredArgsConstructor
@Validated
public class DungeonController {

    private final DungeonService dungeonService;

    /** 1. 특정 던전의 여행지 리스트 */
    @GetMapping("/{dungeonId}/attractions")
    public ResponseEntity<List<Attraction>> getAttractions(
            @PathVariable Integer dungeonId) {
        return ResponseEntity.ok(
            dungeonService.getAttractions(dungeonId)
        );
    }

    /** 2. 특정 던전의 대표 여행지 (랜덤) */
    @GetMapping("/{dungeonId}/attractions/random")
    public ResponseEntity<Attraction> getRandomAttraction(
            @PathVariable Integer dungeonId) {
        var attraction = dungeonService.getRandomAttraction(dungeonId);
        if (attraction == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(attraction);
    }

    /** 3. 검색 조건에 따른 여행지 리스트 */
    @PostMapping("/attractions/search")
    public ResponseEntity<List<Attraction>> searchAttractions(
            @RequestBody @Valid AttractionSearchCondition cond) {
        return ResponseEntity.ok(
            dungeonService.searchAttractions(cond)
        );
    }

    /** 4. 검색된 여행지를 포함하는 던전 리스트 */
    @PostMapping("/search")
    public ResponseEntity<List<Dungeon>> searchDungeonsByAttractions(
            @RequestBody @Valid AttractionSearchCondition cond) {
        return ResponseEntity.ok(
            dungeonService.findDungeonsByCondition(cond)
        );
    }
}
