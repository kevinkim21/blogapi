package com.example.components.naver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import com.example.common.APIConstants;
import com.example.components.naver.dto.NaverBlogItemDto;
import com.example.components.naver.dto.NaverBlogSearchDto;
import com.example.components.naver.dto.NaverBlogResultDto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest
class NaverComponentTest {

  private final String BLOG_SEARCH_API_URL = "https://developers.naver.com/proxyapi/openapi/v1/search/blog?";

  @Autowired
  private NaverComponent naverComponent;

  @MockBean
  private RestTemplate restTemplate;

  @Test
  @DisplayName("네이버 블로그 API 호출 성공 테스트")
  void searchBlogs_Success() {
    // given
    NaverBlogSearchDto searchDto = NaverBlogSearchDto.builder()
        .display(10)
        .start(1)
        .query("spring boot")
        .sort("sim")
        .build();

    HttpHeaders headers = new HttpHeaders();
    headers.set("X-Naver-Client-Id", "id test");
    headers.set("X-Naver-Client-Secret", "secret test");

    UriComponents uriComponents = UriComponentsBuilder
        .fromUriString(BLOG_SEARCH_API_URL)
        .queryParam("query", searchDto.getQuery())
        .queryParam("display",searchDto.getDisplay())
        .queryParam("start",searchDto.getStart())
        .queryParam("sort",searchDto.getSort())
        .queryParam("filter",searchDto.getFilter())
        .build();

    NaverBlogResultDto naverBlogResultDto = NaverBlogResultDto.builder()
        .items(Arrays.asList(
            NaverBlogItemDto.builder()
                .bloggerlink("http://localhost.com")
                .build()
        ))
        .build();
    ResponseEntity<NaverBlogResultDto> responseEntity = ResponseEntity.ok(naverBlogResultDto);

    given(restTemplate.exchange(
        eq(uriComponents.toUri()),
        eq(HttpMethod.GET),
        any(HttpEntity.class),
        eq(NaverBlogResultDto.class)
    )).willReturn(responseEntity);

    // when
    NaverBlogResultDto result = naverComponent.searchBlogs(searchDto);

    // then
    assertThat(result.getItems()).isNotEmpty();
    assertThat(result.getCode()).isEqualTo(APIConstants.API_CALL_SUCCESS_CODE);
    assertThat(result.getMsg()).isEqualTo(APIConstants.API_CALL_SUCCESS_MSG);
  }
}