package com.edu.flux.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class SelfFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        Mono<WebSession> session = exchange.getSession();
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 需要注意的是，由于采用的是异步的处理方式，这时候，我们的后置处理需要放在
        // doFinally() 中进行
        log.info("拦截请求，前置处理--------------------------------");
        return chain.filter(exchange)
            .doOnError((err) -> {
               log.info("目标方法异常了: {}", err.getMessage() );
            })
            .doFinally((signal) -> {
                log.info("结束,后置处理------------------------------------");
            });
    }
}
