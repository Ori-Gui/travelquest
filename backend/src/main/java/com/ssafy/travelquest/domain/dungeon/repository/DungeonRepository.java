package com.ssafy.travelquest.domain.dungeon.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.travelquest.domain.attraction.entity.Attraction;
import com.ssafy.travelquest.domain.dungeon.entity.Dungeon;

@Mapper
public interface DungeonRepository {
	List<Attraction> getAttractionsByDungeonId(Integer dungeonId);
	List<Dungeon> findDungeonsByAttractionIds(List<Integer> ids); 
}
