//package com.ssafy.travelquest.domain.quest.service;
//
//import java.util.List;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ssafy.travelquest.domain.attraction.entity.Attraction;
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class QuestGenerationService {
//  private final WebClient openAiWebClient;
//  private final ObjectMapper objectMapper;
//
//  public record QuestPrompt(String title, String description) {}
//
//  public List<QuestPrompt> generateQuests(List<Attraction> attractions) throws Exception {
//    // 1) Attractions 리스트를 JSON 으로 직렬화
//    String attractionsJson = objectMapper.writeValueAsString(attractions);
//
//    // 2) ChatCompletion 요청 payload
//    var payload = """
//      {
//        "model":"gpt-4o-mini",
//        "messages":[
//          {"role":"system","content":"당신은 여행 퀘스트 기획자입니다."},
//          {"role":"user","content":"다음 명소 정보를 보고, 각 명소별로 {\"title\":\"...\",\"description\":\"...\"} 형식의 JSON 배열을 출력하세요: %s"}
//        ],
//        "temperature":0.7
//      }
//      """.formatted(attractionsJson);
//
//    // 3) 요청 및 응답 추출
//    String responseText = openAiWebClient.post()
//      .uri("/chat/completions")
//      .bodyValue(payload)
//      .retrieve()
//      .bodyToMono(JsonNode.class)
//      .map(root -> root
//          .path("choices").get(0)
//          .path("message").path("content").asText()
//      )
//      .block();
//
//    // 4) JSON 파싱 → QuestPrompt 리스트
//    return objectMapper.readValue(
//      responseText,
//      objectMapper.getTypeFactory().constructCollectionType(List.class, QuestPrompt.class)
//    );
//  }
//}
