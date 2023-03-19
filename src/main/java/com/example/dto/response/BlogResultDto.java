package com.example.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "블로그 검색 결과")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogResultDto {

  @Schema(description = "검색 결과 페이지 정보", nullable = false)
  private PageDto page;

  @Schema(description = "검색 결과 전체 블로그 리스트 정보", nullable = false)
  private List<BlogDto> items;

}
