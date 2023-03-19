package com.example.components.kakao.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoBlogMetaDto {
  @JsonProperty("total_count")
  private int totalCount;

  @JsonProperty("pageable_count")
  private int pageableCount;

  @JsonProperty("is_end")
  private boolean isEnd;
}
