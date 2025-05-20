package com.ssafy.travelquest.domain.attraction.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.travelquest.domain.attraction.dto.AttractionSearchCondition;
import com.ssafy.travelquest.domain.attraction.entity.Attraction;
import com.ssafy.travelquest.domain.attraction.entity.ContentType;
import com.ssafy.travelquest.domain.attraction.entity.Gugun;
import com.ssafy.travelquest.domain.attraction.entity.Sido;
import com.ssafy.travelquest.domain.attraction.repository.AttractionRepository;

import lombok.*;

@Service
@RequiredArgsConstructor
public class AttractionService {
    private final AttractionRepository repo;

    public List<Sido> getSidoList() {
        return repo.getSidoList();
    }

    public List<Gugun> getGugunList(Integer sidoCode) {
        return repo.getGugunListBySido(sidoCode);
    }

    public List<ContentType> getContentTypes() {
        return repo.getContentTypeList();
    }

    public List<Attraction> searchAttractions(AttractionSearchCondition condition) {
        return repo.searchAttractions(condition);
    }
}
