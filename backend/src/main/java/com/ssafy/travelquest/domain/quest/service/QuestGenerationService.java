package com.ssafy.travelquest.domain.quest.service;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.travelquest.global.handler.exception.QuestGenerationException;
import com.ssafy.travelquest.domain.attraction.entity.Attraction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestGenerationService {

    private final ObjectMapper objectMapper;
    private final ChatClient.Builder chatClientBuilder;

    public record QuestPrompt(String title, String description) {}

    public List<QuestPrompt> generateQuests(List<Attraction> attractions) {
        try {
            // 1) Attractions 리스트를 JSON으로 직렬화
            String attractionsJson = objectMapper.writeValueAsString(attractions);
            ChatClient chatClient = chatClientBuilder.build();
            
            // 2) ChatClient에 prompt 전송
            var response = chatClient
                .prompt()
                .system(spec -> spec.param("language", "korean")
                                    .param("character", "quest-planner"))
                .user(
                	"당신은 여행 퀘스트 기획자입니다. 각 명소별로 재미있고 창의적인 현실적으로 수행가능한 퀘스트를 기획해 주시길 바랍니다." +
                	"보상에 대한 언급은 하지 말아 주십시오. 정확하게 퀘스트만 설명해주시면 됩니다." +
                	"ex) 특정 포즈 사진 찍기, 파티원들과 다 같은 메뉴 먹기(여행지가 음식점인 경우), 여행지 주변 만보 걷기 등" +
                    "다음 명소 정보를 보고, 각 명소별로 " +
                    "{\"title\":\"...\",\"description\":\"...\"} 형식의 JSON 배열을 출력하세요: " +
                    "반드시 하나의 명소당 하나의 퀘스트를 생성하세요. " +
                    "아래 JSON 배열만 반환하세요. 절대 다른 설명을 덧붙이지 마세요.\n" +
                    attractionsJson
                )
                .call();  // 동기 호출

            // 3) 응답 content(모델의 답변) 획득
            @SuppressWarnings("null")
			String content = response.content()
            	  .replaceAll("(?m)^```[a-z]*", "")
          		  .replaceAll("(?m)^```$", "")
          		  .trim();
            log.debug("GPT 반환값: {}", content);
            		  
            
            // 4) JSON 문자열을 QuestPrompt 리스트로 역직렬화
            return objectMapper.readValue(
                content,
                objectMapper.getTypeFactory()
                            .constructCollectionType(List.class, QuestPrompt.class)
            );
        } catch (Exception ex) {
            throw new QuestGenerationException("퀘스트 생성 중 오류 발생", ex);
        }
    }
}
