package com.example.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "블로그 검색 결과 페이지 정")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PageDto {
  @Schema(description = "검색 결과 전체 갯수", nullable = false, example = "933")
  private int total;

  @Schema(description = "검색 결과 전체 페이지수", nullable = false, example = "2")
  private int pages;

  @Builder
  public PageDto(int total, int pages) {
    this.total += total;
    this.pages = pages;
  }
}
