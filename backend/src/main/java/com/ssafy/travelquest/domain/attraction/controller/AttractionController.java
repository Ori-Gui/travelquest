package com.ssafy.travelquest.domain.attraction.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ssafy.travelquest.domain.attraction.dto.AttractionSearchCondition;
import com.ssafy.travelquest.domain.attraction.entity.Attraction;
import com.ssafy.travelquest.domain.attraction.entity.ContentType;
import com.ssafy.travelquest.domain.attraction.entity.Gugun;
import com.ssafy.travelquest.domain.attraction.entity.Sido;
import com.ssafy.travelquest.domain.attraction.service.AttractionService;

import jakarta.validation.Valid;
import lombok.*;

@RestController
@RequestMapping("/api/v1/attractions")
@RequiredArgsConstructor
@Validated
public class AttractionController {

    private final AttractionService attractionService;

    // 시도 조회
    @GetMapping("/sidos")
    public ResponseEntity<List<Sido>> getSidoList() {
        return ResponseEntity.ok(attractionService.getSidoList());
    }
    
    // 구군 조회
    @GetMapping("/guguns")
    public ResponseEntity<List<Gugun>> getGugunList(@RequestParam int sidoCode) {
        return ResponseEntity.ok(attractionService.getGugunList(sidoCode));
    }
    
    // 컨텐츠 조회
    @GetMapping("/content-types")
    public ResponseEntity<List<ContentType>> getContentTypes() {
        return ResponseEntity.ok(attractionService.getContentTypes());
    }
    
    // 검색 조건
    @PostMapping("/search")
    public ResponseEntity<List<Attraction>> searchAttractions(@RequestBody @Valid AttractionSearchCondition condition) {
        return ResponseEntity.ok(attractionService.searchAttractions(condition));
    }
}
