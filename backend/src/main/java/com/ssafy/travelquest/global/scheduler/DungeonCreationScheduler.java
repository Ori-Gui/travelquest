package com.ssafy.travelquest.global.scheduler;

import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ssafy.travelquest.domain.dungeon.dto.DungeonCreateDto;
import com.ssafy.travelquest.domain.dungeon.entity.Status;
import com.ssafy.travelquest.domain.dungeon.service.DungeonService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DungeonCreationScheduler {

	private final DungeonService dungeonService;
	private final Random random = new Random();
	private final AtomicInteger counter = new AtomicInteger(1);
	
	
	// 분당 던전 n개 생성
//	@Scheduled(cron = "0 * * * * ?")
//	public void createRandomDungeonsTest() {
//		for (int i = 0; i < 1; i++) {
//			createRandomDungeon();
//		}
//	}
	
	// 하루에 던전 10개 생성
	@Scheduled(cron = "0 0 0 * * ?")
	public void createRandomDungeons() {
		for (int i = 0; i < 10; i++) {
			createRandomDungeon();
		}
	}

	public void createRandomDungeon() {
		int idx = counter.getAndIncrement();
		String title = "던전" + idx;

		int durationDays = random.nextInt(9); // 0~8
		int endOffsetDays = random.nextInt(31 - durationDays) + durationDays; // [durationDays .. 30]

		LocalDate now = LocalDate.now();
		LocalDate startDate = now.plusDays(endOffsetDays - durationDays);
		LocalDate endDate = now.plusDays(endOffsetDays);

		int maxPartySize = random.nextInt(7) + 4; // 4~10명

		DungeonCreateDto dto = DungeonCreateDto.builder().title(title).startDate(startDate).endDate(endDate)
				.maxPartySize(maxPartySize).status(Status.OPEN).build();

		dungeonService.createDungeonAndQuests(dto);
	}
}
