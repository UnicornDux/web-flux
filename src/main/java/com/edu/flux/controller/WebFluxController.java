package com.edu.flux.controller;

import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.edu.flux.model.User;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.Map;

/*---------------两种请求处理方式可以共存-------------------------------
 *
 * 运行在 Netty 服务器上 或 servlet 3.1+ 的容器中
 *
*/
@Slf4j
@RestController
@RequestMapping("/flux/")
public class WebFluxController {

  // 注解方式实现
  // 返回值是一个 Mono
  // localhost:8080/webflux/annotation/greet
  @GetMapping("/greet")
  public Mono<String> greet() {
      return Mono.just("hello webflux by annotation!!");
  }

  // 可以返回多个值，使用Flux, 对比传统的数据返回集合数组类型
  @GetMapping("/more") 
  public Flux<String> more() {
    return Flux.just("hello", "-", "world");
  }


  // 兼容大部分的 Spring mvc 的注解
  // @PathVariable
  // @RequestParam
  // @RequestBody
  //  

  @GetMapping("/path/{variable}")
  public Mono<String> path(@PathVariable String variable) {
    log.info("收到参数: {}", variable);
    return Mono.just(variable);
  }


  @PostMapping("/param")
  public Mono<String> param(
    @RequestParam(value = "username", required = true)String username
  ){
    log.info("param: {}", username);
    return Mono.just(username);
  }


  @PostMapping("/form")
  public Mono<String> form(User user) {
    log.info("form: {}", user);
    return Mono.just(user.getUsername());
  }

  @PostMapping("/body") 
  public Mono<String> json(@RequestBody Map<String,String> body){
    return Mono.just(body.get("value"));
  }



  @PostMapping("/exchange")
  public Mono<String> exchange(ServerWebExchange exchange) {
    // exchange 是一个包含 web 中大部分对象的一个容器
    ServerHttpRequest request = exchange.getRequest();
    ServerHttpResponse response = exchange.getResponse();
    Mono<WebSession> session = exchange.getSession();
    return Mono.empty();
  }


  @PostMapping("/session")
  public Mono<String> session(Session session, HttpMethod method) {
    Session.Cookie cookie = session.getCookie();
    return Mono.just(cookie.getName());
  }

}
