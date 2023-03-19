package com.example.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class ErrorDto {

  private final LocalDateTime created = LocalDateTime.now();
  private String msg;
  private String code;

}
