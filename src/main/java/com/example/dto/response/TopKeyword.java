package com.example.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "인기 검색어 객체")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class TopKeyword {
  @Schema(description = "키워드", nullable = false, example = "Spring Kotlin")
  private String query;

  @Schema(description = "검색 횟수", nullable = false, example = "883")
  private Integer count;
}
