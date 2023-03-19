package com.example.components.kakao.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoBlogDto {
  private String title;
  private String contents;
  private String url;
  private String blogname;
  private String thumbnail;
  private String datetime;
}
