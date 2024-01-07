package com.edu.flux.reactor;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

public class Subscribe {

    public static void main(String[] args) {
        Flux<String> flux = Flux.range(1, 8)
                .map(item -> {
                    return "Yes: " + item;
                });


        // 无自定义消费订阅逻辑
        subscribeNone(flux);
        // 有正常消费回调逻辑
        subscribeNormal(flux);
        // 有正常消费和异常处理的回调逻辑
        subscribeNormalAndError(flux);
        // 有正常，异常，结束消费的逻辑
        subscribeFull(flux);
        // 自定义所有的消费逻辑
        subscribeCustom(flux);
    }

    /**
     *  消费订阅
     */
    public static void subscribeNone(Flux<String> flux) {

        // 虽然消费订阅的时候没有做任何事情，但是可以让流流动起来。
        // 1.不传消费逻辑
        flux.subscribe();
    }

    // 正常消费回调的逻辑
    public static void subscribeNormal(Flux<String> flux) {
        // 2.consumer 作为参数, 定义消费逻辑
        //   这里只有流正常处理的时候逻辑才能正常结束，否则程序直接结束
        flux.subscribe(System.out::println);

    }

    // 正常消费的回调和异常处理的回调
    public static void subscribeNormalAndError(Flux<String> flux) {
        // 3. 传入两个 consumer 作为参数
        // 定义正常消费的逻辑
        flux.subscribe(
                System.out::println,
                err -> {
                    // 处理流异常时回调的逻辑
                    System.out.println(err.getMessage());
                }
        );

    }
    public static void subscribeFull(Flux<String> flux) {
        // 4. 传入三个 参数
        flux.subscribe(
                // 元素的消费逻辑
                (val) -> {
                    System.out.println("处理数据" + val);
                },
                // 处理异常的回调逻辑
                (err) -> {
                    System.out.println("消费异常" + err.getMessage());
                },
                // 正常结束时回调逻辑
                () -> {
                    System.out.println("消费结束");
                }
        );
    }

    public static void subscribeCustom(Flux<String> flux) {
        // 5. 传入一个自定义的消费者
        flux.subscribe(new BaseSubscriber<String>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                System.out.println("建立订阅关系");
                request(1);
            }

            @Override
            protected void hookOnNext(String value) {
                System.out.println("数据到来" + value);
                // 获取下一个数据
                request(1);
                // 获取所有的数据
                requestUnbounded();
            }

            @Override
            protected void hookOnComplete() {
                System.out.println("流处理结束");
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                System.out.println("流处理异常了");
            }

            @Override
            protected void hookOnCancel() {
                System.out.println("流被取消了");
            }

            @Override
            protected void hookFinally(SignalType type) {
                System.out.println("流处理收尾");
            }
        });
    }
}
