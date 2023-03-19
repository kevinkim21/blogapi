package com.example.components.kakao;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.common.APIConstants;
import com.example.components.kakao.dto.KakaoBlogResultDto;
import com.example.components.kakao.dto.KakaoBlogSearchDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KakaoComponentItTest {

  @Autowired
  private KakaoComponent kakaoComponent;

  @Test
  public void searchBlogsTest() {
    // given
    String query = "java";
    String sort = "accuracy";
    int page = 1;
    int size = 10;

    KakaoBlogSearchDto searchDto = new KakaoBlogSearchDto(query, sort, page, size);
    // when
    KakaoBlogResultDto resultDto = kakaoComponent.searchBlogs(searchDto);

    // then
    assertThat(resultDto).isNotNull();
    assertThat(resultDto.getCode()).isEqualTo(APIConstants.API_CALL_SUCCESS_CODE);
    assertThat(resultDto.getMsg()).isEqualTo(APIConstants.API_CALL_SUCCESS_MSG);
    assertThat(resultDto.getDocuments().size()).isEqualTo(size);
  }
}