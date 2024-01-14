package com.edu.flux.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Controller
public class RouterFuncApi {

    // RouterFunction 请求路由，
    // Handle 请求处理
    // localhost:8080/function/greet
    @Bean
    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions
                .route()
                .GET(
                        "function/greet",
                        serverRequest -> ok().bodyValue("hello webflux by functional !!"))
                .build();
    }
}
