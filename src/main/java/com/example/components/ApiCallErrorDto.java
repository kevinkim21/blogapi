package com.example.components;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiCallErrorDto {
  private int code;
  private String msg;
}
