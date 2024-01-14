package com.edu.flux.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("exception")
public class ExceptionController {

    @RequestMapping("/error")
    public Mono<String> getException() {
        throw new RuntimeException("测试异常");
    }

    @GetMapping("/problem")
    public ProblemDetail problem() {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setDetail("出错了");
        return problemDetail;
    }

    @GetMapping("/errorRes")
    public ErrorResponse errorRes() {
       return ErrorResponse.builder(
               new RuntimeException("演示异常处理"),
               HttpStatus.BAD_REQUEST,
               "ErrorResponseApi"
       ).build();
    }
}
