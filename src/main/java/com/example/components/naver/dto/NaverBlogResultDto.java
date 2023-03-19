package com.example.components.naver.dto;

import com.example.components.ApiCallErrorDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverBlogResultDto extends ApiCallErrorDto {
  private String lastBuildDate;
  private int total;
  private int start;
  private int display;
  private List<NaverBlogItemDto> items;
}
