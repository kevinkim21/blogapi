package com.example.components.kakao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import com.example.common.APIConstants;
import com.example.components.kakao.dto.KakaoBlogDto;
import com.example.components.kakao.dto.KakaoBlogSearchDto;
import com.example.components.kakao.dto.KakaoBlogResultDto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest
class KakaoComponentTest {

  private String BLOG_SEARCH_API_URL = "https://dapi.kakao.com/v2/search/blog";

  @Autowired
  private KakaoComponent kakaoComponent;

  @MockBean
  private RestTemplate restTemplate;

  @Test
  @DisplayName("카카오 API 호출 성공 테스트")
  void searchBlogs_Success() {
    // given
    KakaoBlogSearchDto searchDto = KakaoBlogSearchDto.builder()
        .query("test query")
        .sort("accuracy")
        .page(1)
        .size(10)
        .build();

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "KakaoAK API_KEY");

    UriComponents uriComponents = UriComponentsBuilder
        .fromUriString(BLOG_SEARCH_API_URL)
        .queryParam("query", searchDto.getQuery())
        .queryParam("sort", searchDto.getSort())
        .queryParam("page", searchDto.getPage())
        .queryParam("size", searchDto.getSize())
        .build();
    KakaoBlogResultDto kakaoBlogResultDto = KakaoBlogResultDto.builder()
        .documents(Arrays.asList(
            KakaoBlogDto.builder()
                .title("test")
                .url("https://localhost")
                .build()
        ))
        .build();
    ResponseEntity<KakaoBlogResultDto> responseEntity = ResponseEntity.ok(kakaoBlogResultDto);

    given(restTemplate.exchange(
        eq(uriComponents.toUri()),
        eq(HttpMethod.GET),
        any(HttpEntity.class),
        eq(KakaoBlogResultDto.class)
    )).willReturn(responseEntity);

    // when
    KakaoBlogResultDto result = kakaoComponent.searchBlogs(searchDto);

    // then
    assertThat(result.getDocuments()).isNotEmpty();
    assertThat(result.getCode()).isEqualTo(APIConstants.API_CALL_SUCCESS_CODE);
    assertThat(result.getMsg()).isEqualTo(APIConstants.API_CALL_SUCCESS_MSG);
  }
}