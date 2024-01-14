package com.edu.flux;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

import java.util.StringJoiner;

@Slf4j
@EnableWebFlux
@SpringBootApplication
public class FluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(FluxApplication.class, args);

        // 自定义服务器
        // Server();
    }


    /**
     * 自定义一个 web 服务器启动
     */
    @SneakyThrows
    public static void Server() {

        HttpHandler httpHandler = (request, response) -> {
            log.info("请求进入, {} 处理请求........", Thread.currentThread().getName());
            // 获取到请求中携带的数据，cookie, 路径, 方法等
            StringJoiner joiner = new StringJoiner("\n");
            joiner.add("");
            joiner.add("local : " + request.getLocalAddress().getHostString());
            joiner.add("remote: " + request.getRemoteAddress().getHostString());
            joiner.add("path  : " + request.getPath().value());
            joiner.add("method: " + request.getMethod());
            joiner.add("params: " + request.getQueryParams().toString());
            joiner.add("body  : " + request.getBody());
            joiner.add("cookie: " + request.getCookies().toString());
            log.info(joiner.toString());

            // 处理请求的响应
            response.setStatusCode(HttpStatus.OK); // 设置响应的状态码
            response.getHeaders();  // 响应头
            response.getCookies();  // cookie
            response.getStatusCode(); // 响应码

            // buffer 工厂, 用于封装返回的 DataBuffer
            DataBufferFactory factory = response.bufferFactory();
            // 封装数据
            DataBuffer buffer = factory.wrap("hello world".getBytes());

            // 直接标识请求处理结束
            // return response.setComplete();

            // 写出数据
            // 把什么内容写出去, 这里需要的是 DataBuffer
            return response.writeWith(Mono.just(buffer));
        };

        // 设置响应式处理器
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        // 绑定监听的端口进行处理
        HttpServer.create()
                .host("localhost")
                .port(8808)
                .handle(adapter)
                .bindNow();

        log.info("服务启动，监听在 8808.....");
        System.in.read();
    }
}
