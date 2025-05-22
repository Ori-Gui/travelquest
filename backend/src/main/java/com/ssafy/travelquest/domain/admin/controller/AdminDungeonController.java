package com.ssafy.travelquest.domain.admin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.travelquest.domain.dungeon.dto.DungeonCreateDto;
import com.ssafy.travelquest.domain.dungeon.entity.Dungeon;
import com.ssafy.travelquest.domain.dungeon.service.DungeonService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Validated
public class AdminDungeonController {

    private final DungeonService dungeonService;

    @PostMapping("/dungeon")
    public ResponseEntity<Dungeon> createDungeon(@RequestBody @Valid DungeonCreateDto dto) {
        Dungeon dungeon = dungeonService.createDungeonAndQuests(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dungeon);
    }
}
