package com.example.controller;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;

import com.example.common.ErrorCode;
import com.example.common.Provider;
import com.example.common.SortType;
import com.example.dto.response.BlogDto;
import com.example.dto.response.BlogResultDto;
import com.example.dto.response.PageDto;
import com.example.dto.response.TopKeyword;
import com.example.exception.BlogSearchException;
import com.example.service.BlogSearchService;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;


@ExtendWith(MockitoExtension.class)
class BlogSearchControllerTest {

  @Mock
  private BlogSearchService blogSearchService;

  @InjectMocks
  private BlogSearchController blogSearchController;

  @Test
  @DisplayName("인기검색어 호출 - 200 OK")
  void testGetTopKeywords() {
    // given
    List<TopKeyword> topKeywords = Arrays.asList(
        new TopKeyword("keyword1", 100),
        new TopKeyword("keyword2", 90)
    );
    given(blogSearchService.findTop10ByOrderByCountDesc()).willReturn(topKeywords);

    // when
    List<TopKeyword> result = blogSearchController.getTopKeywords();

    // then
    assertThat(result).hasSize(2);
    assertThat(result.get(0).getQuery()).isEqualTo("keyword1");
    assertThat(result.get(0).getCount()).isEqualTo(100);
    assertThat(result.get(1).getQuery()).isEqualTo("keyword2");
    assertThat(result.get(1).getCount()).isEqualTo(90);

    verify(blogSearchService).findTop10ByOrderByCountDesc();
  }

  @Test
  @DisplayName("블로그 검색 호출 - 200 OK")
  void testSearchBlog() throws Exception {
    //given
    BlogResultDto givenResult = BlogResultDto.builder()
        .items(Arrays.asList(BlogDto.builder()
                .contents("test")
                .blogName("test")
                .title("test")
                .url("http://localhost")
            .build()))
        .page(PageDto.builder()
            .pages(1)
            .total(1)
            .build())
        .build();
    given(blogSearchService.searchBlog(Provider.KAKAO,"test", SortType.ACCURACY, 1, 1))
        .willReturn(givenResult);
    //when
    BlogResultDto result = blogSearchController.getBlogs(Provider.KAKAO,"test", SortType.ACCURACY, 1, 1);
    //then
    assertThat(result).isEqualTo(givenResult);

    verify(blogSearchService).searchBlog(Provider.KAKAO,"test", SortType.ACCURACY, 1, 1);
  }

  @Test
  @DisplayName("블로그 검색 호출 - 오류 발생")
  void testSearchBlogError() throws Exception {
    //given
    ErrorCode errorCode = ErrorCode.SERVICE_ERROR;
    BlogSearchException exception = new BlogSearchException(errorCode);
    willThrow(exception).given(blogSearchService).searchBlog(Provider.KAKAO, "test", SortType.ACCURACY, 1, 10);

    //when
    try {
      blogSearchController.getBlogs(Provider.KAKAO,"test", SortType.ACCURACY, 1, 10);
    } catch (BlogSearchException e) {
      //then
      assertThat(e.getErrorCode()).isEqualTo(errorCode);
      verify(blogSearchService).searchBlog(Provider.KAKAO, "test", SortType.ACCURACY, 1, 10);
    }
  }
}