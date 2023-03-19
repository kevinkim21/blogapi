package com.example.components.naver;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.common.APIConstants;
import com.example.components.naver.dto.NaverBlogResultDto;
import com.example.components.naver.dto.NaverBlogSearchDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NaverComponentItTest {

  @Autowired
  private NaverComponent naverComponent;

  @Test
  @DisplayName("네이버 블로그 API 호출 테스트")
  void searchBlogIntegrationTest() {
    //given
    NaverBlogSearchDto searchDto = NaverBlogSearchDto.builder()
        .display(10)
        .start(1)
        .query("spring boot")
        .sort("sim")
        .build();

    // when
    NaverBlogResultDto resultDto = naverComponent.searchBlogs(searchDto);

    // then
    assertThat(resultDto).isNotNull();
    assertThat(resultDto.getCode()).isEqualTo(APIConstants.API_CALL_SUCCESS_CODE);
    assertThat(resultDto.getMsg()).isEqualTo(APIConstants.API_CALL_SUCCESS_MSG);
    assertThat(resultDto.getItems().size()).isEqualTo(searchDto.getDisplay());
  }
}