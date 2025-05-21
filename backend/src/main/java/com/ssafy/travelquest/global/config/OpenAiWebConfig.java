//package com.ssafy.travelquest.global.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpHeaders;
//
//
//@Configuration
//public class OpenAiWebConfig {
//	@Bean
//	public WebClient openAiWebClient(@Value("${openai.api.key}") String apiKey) {
//	  return WebClient.builder()
//	    .baseUrl("https://api.openai.com/v1")
//	    .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
//	    .build();
//	}
//}
