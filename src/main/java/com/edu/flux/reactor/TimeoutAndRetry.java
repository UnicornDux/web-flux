package com.edu.flux.reactor;

import lombok.SneakyThrows;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class TimeoutAndRetry {

    @SneakyThrows
    public static void main(String[] args) {
        // timeout();
        retry();
        System.in.read();
    }

    // retry 一般表示异常之后重试几次 (不传参数表示一直重试)
    // 这里重试的表示重流的起始位置重新消费一次，不是从异常的地方继续
    public static void retry() {
        Flux.just(1, 2, 0, 8)
                .map(i -> 100 / i)
                .log()
                .retry(3)
                .subscribe(
                    System.out::println,
                    (err) -> System.out.println("err: " + err.getMessage())
                );
    }

    public static void timeout() {
        Flux.interval(Duration.ofSeconds(1))
            .timeout(Duration.ofMillis(900))
            .onErrorMap(err -> new RuntimeException("timeout"))
            .log()
            .subscribe(
                System.out::println,
                (err) -> System.out.println("err: " + err.getMessage())
            );
    }

}
