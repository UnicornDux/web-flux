package com.edu.flux.controller;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/sse")
public class SseController {

  @GetMapping(value = "/sendMsg", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<String> sendMsg() {
    return Flux.range(1, 10)
        .map(i -> "message" + i)
        .delayElements(Duration.ofMillis(900));
  }

  @GetMapping("/sendEvent")
  public Flux<ServerSentEvent<String>> sendEvent() {
    return Flux.range(1, 10)
        .map(i -> {
          // 真实的 ServerSentEvent 对象有 id, data, event retry, comment 等等的属性
          // 如果需要做一个真正的接口，应该返回一个比较完成的对象信息
            return ServerSentEvent.builder("value" + i)
              .id(i + "")
              .comment("msg")
              .event("message")
              .build();
        }).delayElements(Duration.ofMillis(900));
  }
}
