package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig {
  @Bean
  RestTemplate restTemplate() {
    return new RestTemplateBuilder()
        .build();
  }

  @Bean
  HttpHeaders kakaoHttpHeaders(@Value("${kakao.api.key}") String apiKey) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "KakaoAK " + apiKey);
    return headers;
  }

  @Bean
  HttpHeaders naverHttpHeaders(
      @Value("${naver.api.client.id}") String id,
      @Value("${naver.api.client.secret}") String secret) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("X-Naver-Client-Id", id);
    headers.set("X-Naver-Client-Secret", secret);
    return headers;
  }
}
