package com.example.components.kakao.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum KakaoBlogSearchSortType {

  ACCURACY("accuracy"),
  RECENCY("recency"),
  ;
  private String sort;

}
