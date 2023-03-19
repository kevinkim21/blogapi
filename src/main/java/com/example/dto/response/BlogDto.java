package com.example.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "블로그 정보")
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class BlogDto {
  @Schema(description = "블로그 타이틀")
  private String title;

  @Schema(description = "블로그 주소")
  private String url;

  @Schema(description = "블로그 컨텐츠")
  private String contents;

  @Schema(description = "블로그 이름")
  private String blogName;

  @Schema(description = "포스팅 일자")
  private String posted;
}
