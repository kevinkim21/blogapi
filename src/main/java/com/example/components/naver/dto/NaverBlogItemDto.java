package com.example.components.naver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverBlogItemDto {
  private String title;
  private String link;
  private String description;
  private String bloggername;
  private String bloggerlink;
  private String postdate;
}
