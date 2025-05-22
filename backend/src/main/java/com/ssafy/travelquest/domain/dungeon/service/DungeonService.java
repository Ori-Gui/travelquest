package com.ssafy.travelquest.domain.dungeon.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.travelquest.global.handler.exception.DungeonCreationException;
import com.ssafy.travelquest.global.handler.exception.QuestGenerationException;
import com.ssafy.travelquest.domain.attraction.entity.Attraction;
import com.ssafy.travelquest.domain.dungeon.dto.DungeonCreateDto;
import com.ssafy.travelquest.domain.dungeon.dto.DungeonSearchCondition;
import com.ssafy.travelquest.domain.dungeon.entity.Dungeon;
import com.ssafy.travelquest.domain.dungeon.repository.DungeonRepository;
import com.ssafy.travelquest.domain.quest.entity.Quest;
import com.ssafy.travelquest.domain.quest.entity.QuestStatus;
import com.ssafy.travelquest.domain.quest.repository.QuestRepository;
import com.ssafy.travelquest.domain.quest.service.QuestGenerationService;
import com.ssafy.travelquest.domain.quest.service.QuestGenerationService.QuestPrompt;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DungeonService {

	private final DungeonRepository dungeonRepo;
	private final QuestGenerationService generationService;
	private final QuestRepository questRepo;

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

	/** 던전의 대표 여행지 */
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
		final int dungeonId;
		try {
			dungeonId = dungeonRepo.createDungeonViaProcedure(dto);
		} catch (Exception ex) {
			throw new DungeonCreationException("던전 생성 프로시저 실행 중 오류 발생", ex);
		}

		// 2) 연결된 attractions 조회
		List<Attraction> attractions = dungeonRepo.getAttractionsByDungeonId(dungeonId);

		// 3) GPT로 퀘스트 생성
		List<QuestPrompt> prompts = generationService.generateQuests(attractions);

		// 4) 퀘스트 결과 검증
		if (prompts.isEmpty()) {
			throw new DungeonCreationException("생성된 퀘스트가 없습니다.");
		}
		if (prompts.size() != attractions.size()) {
		    throw new QuestGenerationException(
		        String.format("퀘스트 개수 불일치: 명소 %d개 vs 퀘스트 %d개", 
		                      attractions.size(), prompts.size())
		    );
		}

		// 5) Quest 삽입
		for (int i = 0; i < prompts.size(); i++) {
			QuestPrompt p = prompts.get(i);
			Attraction a = attractions.get(i);
			Quest q = Quest.builder().dungeonId(dungeonId).attractionId(a.getNo()).title(p.title())
					.description(p.description()).orderIndex(i + 1).status(QuestStatus.PENDING).build();
			questRepo.insertQuest(q);
		}

		// 6) 생성된 던전 리턴
		return dungeonRepo.getDungeonById(dungeonId);
	}
}
