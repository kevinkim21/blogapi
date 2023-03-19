package com.example.components.naver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NaverBlogSearchDto {
  private String query;
  private int display;
  private int start;
  private String sort;
  private final String filter = "all";
}
