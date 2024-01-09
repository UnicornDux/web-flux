package com.edu.flux.reactor;

import reactor.core.publisher.Flux;

/**
 * 自定义数据处理
 */
public class Handle {

    public static void main(String[] args) {
        Flux.range(1, 10)
                .handle(
                    // value 接受上游的流入的数据
                    // sink 可以将数据继续往下流出
                    (value, sink) -> {
                        System.out.println("获取到流入的数据: " + value);
                        sink.next("用户: " + value);
                    }
                )
                .log()
                .subscribe();
    }


}
