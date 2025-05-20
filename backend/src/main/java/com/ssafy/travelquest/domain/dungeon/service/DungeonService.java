package com.ssafy.travelquest.domain.dungeon.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ssafy.travelquest.domain.attraction.dto.AttractionSearchCondition;
import com.ssafy.travelquest.domain.attraction.entity.Attraction;
import com.ssafy.travelquest.domain.attraction.service.AttractionService;
import com.ssafy.travelquest.domain.dungeon.entity.Dungeon;
import com.ssafy.travelquest.domain.dungeon.repository.DungeonRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DungeonService {

    private final DungeonRepository dungeonRepo;
    private final AttractionService attractionService;
    private final Random rnd = new Random();

    /** 1. 던전의 여행지 리스트 */
    public List<Attraction> getAttractions(Integer dungeonId) {
        return dungeonRepo.getAttractionsByDungeonId(dungeonId);
    }

    /** 2. 던전의 대표 여행지 (랜덤) */
    public Attraction getRandomAttraction(Integer dungeonId) {
        var list = getAttractions(dungeonId);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(rnd.nextInt(list.size()));
    }

    /** 3. 검색 조건에 따른 여행지 리스트 */
    public List<Attraction> searchAttractions(AttractionSearchCondition cond) {
        return attractionService.searchAttractions(cond);
    }

    /** 4. 검색된 여행지 중 하나라도 포함하는 던전 리스트 */
    public List<Dungeon> findDungeonsByCondition(AttractionSearchCondition cond) {
        var attractions = searchAttractions(cond);
        var ids = attractions.stream()
                             .map(Attraction::getNo)
                             .collect(Collectors.toList());
        if (ids.isEmpty()) {
            return List.of();
        }
        return dungeonRepo.findDungeonsByAttractionIds(ids);
    }
}
