package com.edu.flux.reactor;

import reactor.core.publisher.Flux;

/**
 * 限流
 */
public class LimitRate {

    public static void main(String[] args) {

        Flux.range(1, 1000)
                .log()
                .limitRate(100)
                .subscribe();

        // 看到打印的日志, 这里的抓取策略是
        // 1. 第一次取出 100 个
        // 2. 当数据处理了 75% 的时候，就开始再次抓取
        // 3. 再次抓取的时候抓取的是 75 个而不是 100
    }
}
