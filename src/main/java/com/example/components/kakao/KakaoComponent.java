package com.example.components.kakao;

import com.example.common.APIConstants;
import com.example.common.ErrorCode;
import com.example.components.kakao.dto.KakaoBlogResultDto;
import com.example.components.kakao.dto.KakaoBlogSearchDto;
import com.example.exception.BlogSearchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
public class KakaoComponent {

  private static final String BLOG_SEARCH_API_URL = "https://dapi.kakao.com/v2/search/blog";

  private RestTemplate restTemplate;
  private HttpHeaders kakaoHttpHeaders;

  public KakaoComponent(RestTemplate restTemplate, HttpHeaders kakaoHttpHeaders) {
    this.restTemplate = restTemplate;
    this.kakaoHttpHeaders = kakaoHttpHeaders;
  }

  public KakaoBlogResultDto searchBlogs(KakaoBlogSearchDto searchDto) {
    UriComponents uriComponents = UriComponentsBuilder
        .fromUriString(BLOG_SEARCH_API_URL)
        .queryParam("query", searchDto.getQuery())
        .queryParam("sort",searchDto.getSort())
        .queryParam("page", String.valueOf(searchDto.getPage()))
        .queryParam("size", String.valueOf(searchDto.getSize()))
        .build();
    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(kakaoHttpHeaders);
    try {
      ResponseEntity<KakaoBlogResultDto> response = restTemplate.exchange(
          uriComponents.toUri(),
          HttpMethod.GET,
          request,
          KakaoBlogResultDto.class);
      KakaoBlogResultDto result =  response.getBody();
      result.setCode(APIConstants.API_CALL_SUCCESS_CODE);
      result.setMsg(APIConstants.API_CALL_SUCCESS_MSG);
      return result;
    } catch (RestClientException e) {
      throw new BlogSearchException(ErrorCode.KAKAO_SERVICE_ERROR);
    }
  }

}
