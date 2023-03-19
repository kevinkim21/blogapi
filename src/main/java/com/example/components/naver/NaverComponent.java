package com.example.components.naver;

import com.example.common.APIConstants;
import com.example.common.ErrorCode;
import com.example.components.naver.dto.NaverBlogItemDto;
import com.example.components.naver.dto.NaverBlogSearchDto;
import com.example.components.naver.dto.NaverBlogResultDto;
import com.example.exception.BlogSearchException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
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
public class NaverComponent {

  private static final String BLOG_SEARCH_API_URL = "https://developers.naver.com/proxyapi/openapi/v1/search/blog?";

  private RestTemplate restTemplate;
  private HttpHeaders naverHttpHeaders;

  public NaverComponent(RestTemplate restTemplate, HttpHeaders naverHttpHeaders) {
    this.restTemplate = restTemplate;
    this.naverHttpHeaders = naverHttpHeaders;
  }

  public NaverBlogResultDto searchBlogs(NaverBlogSearchDto searchDto) {
    UriComponents uriComponents = UriComponentsBuilder
        .fromUriString(BLOG_SEARCH_API_URL)
        .queryParam("query", searchDto.getQuery())
        .queryParam("display",searchDto.getDisplay())
        .queryParam("start",searchDto.getStart())
        .queryParam("sort",searchDto.getSort())
        .queryParam("filter",searchDto.getFilter())
        .build();
    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(naverHttpHeaders);
    try {
      ResponseEntity<NaverBlogResultDto> response = restTemplate.exchange(
          uriComponents.toUri(),
          HttpMethod.GET,
          request,
          NaverBlogResultDto.class);
      NaverBlogResultDto result = response.getBody();
      result.setCode(APIConstants.API_CALL_SUCCESS_CODE);
      result.setMsg(APIConstants.API_CALL_SUCCESS_MSG);
      return result;
    } catch (RestClientException e) {
      log.error("naver api call fail. {}",e);
      throw new BlogSearchException(ErrorCode.NAVER_SERVICE_ERROR);
    }
  }
}
