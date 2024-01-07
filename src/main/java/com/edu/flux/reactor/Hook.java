package com.edu.flux.reactor;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

import java.time.Duration;

public class Hook {

    public static void main(String[] args) {
        hook();
    }

    /**
     * > ------------------------
     *    钩子函数
     *   ------------------------
     *  - 基于 Flux / Mono 产生流由 (elements + single) 共同组成
     *  - elements 就是流中需要进行处理的数据
     *  - single 是处理数据中产生的信号，可以建立事件监听机制，并注册对应的钩子函数，从而实现在特定的时机触发对应的事件
     *  - 这些事件一般在 API 中一般以 `doOnxxx` 出现，被称为钩子函数
     */
    public static void hook() {

        // 使用 range 产生一个 Flux 类型的数据流
        Flux<Integer> flux = Flux.range(1, 8)
                // 这个方法是对上一个流数据的处理，产生一个新流，这样的过程我们称之为转流
                .delayElements(Duration.ofSeconds(1))

                // 开始注册钩子函数
                .doOnComplete(() -> {
                    System.out.println("流处理结束");
                }).doOnCancel(() -> {
                    System.out.println("流取消订阅");
                }).doOnNext((integer) -> {
                    System.out.println("doOnNext" + integer);
                }).doOnError( throwable -> {
                    System.out.println("stream error： " + throwable);
                });
        // 流只有在被订阅的时候才会生效，否则不会产生数据流, 代码不会执行
        // flux.subscribe(System.out::println);


        /**
         * 每一个流都可以注册并触发事件
         */
        // 使用 just 产生一个 Flux 类型的数据流
        Flux<Integer> value = Flux.just(1, 2, 5, 8, 0, 10, 2)
                // 这里 onNext 监听的是 just 产生的数据流
                .doOnNext((integer) -> {
                    System.out.println("doOnNext" + integer);
                })
                .doOnComplete(() -> {
                    System.out.println("流处理结束");
                })
                // 这个方法是对上一个流数据的处理，产生一个新流，这样的过程我们称之为转流
                // 处理完之后产生的新的数据流
                .delayElements(Duration.ofSeconds(1))
                .doOnComplete(() -> {
                    System.out.println("流处理结束");
                })
                .doOnNext((integer) -> {
                    System.out.println("消费到元素: " + integer);
                })
                // 开始注册钩子函数, 这里注册的监听都是针对上游的 delayElements 产生的新的数据流
                .doOnCancel(() -> {
                    System.out.println("流取消订阅");
                }).doOnError(throwable -> {
                    System.out.println("stream error： " + throwable.getMessage());
                });
        // 流只有在被订阅的时候才会生效，否则不会产生数据流, 代码不会执行
        value.subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                System.out.println("开始订阅: " + subscription);
                subscription.request(1);
            }

            @Override
            protected void hookOnNext(Integer value) {
                System.out.println("元素到达: " + value);
                request(1);
            }

            @Override
            protected void hookOnComplete() {
                System.out.println("流处理结束");
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                System.out.println("流处理发生异常");
            }

            @Override
            protected void hookOnCancel() {
                System.out.println("流处理取消");
            }

            @Override
            protected void hookFinally(SignalType type) {
                System.out.println("流处理收尾工作");
            }
        });
    }
}
