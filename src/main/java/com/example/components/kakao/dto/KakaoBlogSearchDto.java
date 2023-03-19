package com.example.components.kakao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoBlogSearchDto {
  private String query;
  private String sort;
  private int page;
  private int size;
}
