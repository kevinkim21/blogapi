package com.example.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  SERVICE_ERROR(1000, "api service error."),
  BAD_REQUEST(1001, "bad request"),
  NOT_FOUND_ERROR(4001, "not found search result."),
  KAKAO_SERVICE_ERROR(5001, "kakao service error."),
  NAVER_SERVICE_ERROR(5002, "naver service error."),
  ;
  private int code;
  private String msg;
}
