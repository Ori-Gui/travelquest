package com.ssafy.travelquest.domain.dungeon.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.travelquest.domain.attraction.dto.AttractionSearchCondition;
import com.ssafy.travelquest.domain.attraction.entity.Attraction;
import com.ssafy.travelquest.domain.attraction.service.AttractionService;
import com.ssafy.travelquest.domain.dungeon.dto.DungeonCreateDto;
import com.ssafy.travelquest.domain.dungeon.dto.DungeonSearchCondition;
import com.ssafy.travelquest.domain.dungeon.entity.Dungeon;
import com.ssafy.travelquest.domain.dungeon.repository.DungeonRepository;
import com.ssafy.travelquest.domain.quest.entity.Quest;
import com.ssafy.travelquest.domain.quest.entity.QuestStatus;
//import com.ssafy.travelquest.domain.quest.service.QuestGenerationService;

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
    
    @Transactional
    public Dungeon createDungeonAndQuests(DungeonCreateDto dto) {
        // 1) 던전 생성 via 프로시저
        int dungeonId = dungeonRepo.createDungeonViaProcedure(dto);

        // 2) 연결된 attractions 조회
        List<Attraction> list = dungeonRepo.getAttractionsByDungeonId(dungeonId);

//        // 3) GPT로 퀘스트 생성
//        var prompts = generationService.generateQuests(list);
//
//        // 4) Quest 삽입
//        for (int i = 0; i < prompts.size(); i++) {
//            var p = prompts.get(i);
//            Quest q = Quest.builder()
//                .dungeonId(dungeonId)
//                .attractionId(list.get(i).getNo())
//                .title(p.title())
//                .description(p.description())
//                .orderIndex(i + 1)
//                .status(QuestStatus.PENDING)
//                .build();
//            questRepo.insertQuest(q);
//        }

        // 5) 생성된 던전 리턴
        return dungeonRepo.getDungeonById(dungeonId);
    }
}
