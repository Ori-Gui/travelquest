package com.ssafy.travelquest.domain.dungeon.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.travelquest.domain.attraction.dto.AttractionSearchCondition;
import com.ssafy.travelquest.domain.attraction.entity.Attraction;
import com.ssafy.travelquest.domain.attraction.service.AttractionService;
import com.ssafy.travelquest.domain.dungeon.dto.DungeonSearchCondition;
import com.ssafy.travelquest.domain.dungeon.entity.Dungeon;
import com.ssafy.travelquest.domain.dungeon.repository.DungeonRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DungeonService {

    private final DungeonRepository dungeonRepo;
    private final AttractionService attractionService;
    
    /** 던전 기본 정보 단일 조회 */
    @Transactional(readOnly = true)
    public Dungeon getDungeonById(Integer dungeonId) {
        return dungeonRepo.getDungeonById(dungeonId);
    }

    /** 던전의 여행지 리스트 */
    @Transactional(readOnly = true)
    public List<Attraction> getAttractions(Integer dungeonId) {
        return dungeonRepo.getAttractionsByDungeonId(dungeonId);
    }

    /** 던전의 대표 여행지  */
    @Transactional(readOnly = true)
    public Attraction getFirstAttraction(Integer dungeonId) {
        var list = getAttractions(dungeonId);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /** 검색된 여행지 중 하나라도 포함하는 던전 리스트 */
    @Transactional(readOnly = true)
    public List<Dungeon> findDungeonsByCondition(DungeonSearchCondition cond) {
    	var list = dungeonRepo.findDungeonsByCondition(cond);
        return list;
    }
}
