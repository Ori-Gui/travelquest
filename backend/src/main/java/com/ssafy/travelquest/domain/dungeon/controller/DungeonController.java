package com.ssafy.travelquest.domain.dungeon.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ssafy.travelquest.domain.attraction.dto.AttractionSearchCondition;
import com.ssafy.travelquest.domain.attraction.entity.Attraction;
import com.ssafy.travelquest.domain.dungeon.dto.DungeonCreateDto;
import com.ssafy.travelquest.domain.dungeon.dto.DungeonSearchCondition;
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
    
    /** 특정 던전 기본 정보 조회 */
    @GetMapping("/{dungeonId}")
    public ResponseEntity<Dungeon> getDungeonById(
            @PathVariable Integer dungeonId) {
        Dungeon dungeon = dungeonService.getDungeonById(dungeonId);
        if (dungeon == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dungeon);
    }

    /** 특정 던전의 여행지 리스트 */
    @GetMapping("/{dungeonId}/attractions")
    public ResponseEntity<List<Attraction>> getAttractions(
            @PathVariable Integer dungeonId) {
        return ResponseEntity.ok(
            dungeonService.getAttractions(dungeonId)
        );
    }

    /** 특정 던전의 대표 여행지 */
    @GetMapping("/{dungeonId}/attractions/first")
    public ResponseEntity<Attraction> getRandomAttraction(
            @PathVariable Integer dungeonId) {
        var attraction = dungeonService.getFirstAttraction(dungeonId);
        if (attraction == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(attraction);
    }

    /** 검색된 여행지를 포함하는 던전 리스트 */
    @PostMapping("/search")
    public ResponseEntity<List<Dungeon>> searchDungeonsByAttractions(
            @RequestBody @Valid DungeonSearchCondition cond) {
        return ResponseEntity.ok(
            dungeonService.findDungeonsByCondition(cond)
        );
    }
    
    @PostMapping
    public ResponseEntity<Dungeon> createDungeon(@RequestBody @Valid DungeonCreateDto dto) {
      Dungeon d = dungeonService.createDungeonAndQuests(dto);
      return ResponseEntity.status(HttpStatus.CREATED).body(d);
    }
}
