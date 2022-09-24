package com.edu.flux.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@RestController
@RequestMapping("/webflux/")
public class WebFluxController {

    /*---------------两种请求处理方式可以共存-------------------------------
     *
     * 运行在 Netty 服务器上 或 servlet 3.1+ 的容器中
     *
     */

    // 注解方式实现
    // 返回值是一个 Mono
    // localhost:8080/webflux/annotation/greet
    @GetMapping("annotation/greet")
    public Mono<String> greet(){
        return Mono.just("hello webflux by annotation!!");
    }

    // RouterFunction 请求路由，
    // Handle 请求处理
    // localhost:8080/function/greet
    @Bean
    public RouterFunction<ServerResponse> routes(){
        return RouterFunctions
            .route()
            .GET(
                "function/greet",
                serverRequest -> ok().bodyValue("hello webflux by functional !!")
            ).build();
    }
}
