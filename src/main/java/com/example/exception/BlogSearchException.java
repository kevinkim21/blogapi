package com.example.exception;

import com.example.common.ErrorCode;
import lombok.Getter;

@Getter
public class BlogSearchException extends RuntimeException{

  private ErrorCode errorCode;

  public BlogSearchException(ErrorCode errorCode) {
    super(errorCode.getMsg());
    this.errorCode = errorCode;
  }
}
