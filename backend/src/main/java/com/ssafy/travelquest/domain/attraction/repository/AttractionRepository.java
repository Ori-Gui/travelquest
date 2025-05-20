package com.ssafy.travelquest.domain.attraction.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.travelquest.domain.attraction.dto.AttractionSearchCondition;
import com.ssafy.travelquest.domain.attraction.entity.Attraction;
import com.ssafy.travelquest.domain.attraction.entity.ContentType;
import com.ssafy.travelquest.domain.attraction.entity.Gugun;
import com.ssafy.travelquest.domain.attraction.entity.Sido;

@Mapper
public interface AttractionRepository {
    List<Sido> getSidoList();
    List<Gugun> getGugunListBySido(int sidoCode);
    List<ContentType> getContentTypeList();
    List<Attraction> searchAttractions(AttractionSearchCondition condition);
}

