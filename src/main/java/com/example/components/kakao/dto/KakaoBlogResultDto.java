package com.example.components.kakao.dto;

import com.example.components.ApiCallErrorDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoBlogResultDto extends ApiCallErrorDto {
  private KakaoBlogMetaDto meta;
  private List<KakaoBlogDto> documents;
}
