package com.example.controller;

import com.example.common.ErrorCode;
import com.example.dto.response.ErrorDto;
import com.example.exception.BlogSearchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

  @ExceptionHandler(BlogSearchException.class)
  protected ResponseEntity<ErrorDto> handleBlogSearchException(BlogSearchException e) {
    log.error("handleBlogSearchException. {}", e);
    ErrorDto response = ErrorDto.builder()
        .code(String.valueOf(e.getErrorCode().getCode()))
        .msg(e.getErrorCode().getMsg())
        .build();
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  protected ResponseEntity<ErrorDto> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
    log.error("handleHttpRequestMethodNotSupportedException. {}", e);
    ErrorDto response = ErrorDto.builder()
        .code(String.valueOf(ErrorCode.SERVICE_ERROR.getCode()))
        .msg(ErrorCode.SERVICE_ERROR.getMsg())
        .build();
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ErrorDto> handleException(Exception e) {
    log.error("handleException. {}", e);
    ErrorDto response = ErrorDto.builder()
        .code(String.valueOf(ErrorCode.SERVICE_ERROR.getCode()))
        .msg(ErrorCode.SERVICE_ERROR.getMsg())
        .build();
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  protected ResponseEntity<ErrorDto> handleNoHandlerFoundException(NoHandlerFoundException e) {
    log.error("handleNoHandlerFoundException. {}", e);
    ErrorDto response = ErrorDto.builder()
        .code(String.valueOf(ErrorCode.BAD_REQUEST.getCode()))
        .msg(ErrorCode.BAD_REQUEST.getMsg())
        .build();
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    log.error("handleNoHandlerFoundException. {}", e);
    ErrorDto response = ErrorDto.builder()
        .code(String.valueOf(ErrorCode.BAD_REQUEST.getCode()))
        .msg(ErrorCode.BAD_REQUEST.getMsg())
        .build();
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  protected ResponseEntity<ErrorDto> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
    log.error("handleNoHandlerFoundException. {}", e);
    ErrorDto response = ErrorDto.builder()
        .code(String.valueOf(ErrorCode.BAD_REQUEST.getCode()))
        .msg(ErrorCode.BAD_REQUEST.getMsg())
        .build();
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
