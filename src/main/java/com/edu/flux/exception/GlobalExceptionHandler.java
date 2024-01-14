package com.edu.flux.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ArithmeticException.class)
  public Mono<String> handlerArithemeticException(ArithmeticException ex) {
    return Mono.just("产生数学运算异常");
  }


  @ExceptionHandler(Exception.class) 
  public Mono<String> handleException(Exception ex){
    return Mono.just("系统运行异常: " + ex.getMessage());
  }
}
