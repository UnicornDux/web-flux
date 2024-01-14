package com.edu.flux.reactor;

import lombok.SneakyThrows;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 */
public class SinksUtils {

    @SneakyThrows
    public static void main(String[] args) {
        // unicast();
        // multiCase();
        // replay();
        // cache();
        // block();
        parallelFlux();
        System.in.read();
    }

    // 数据广播
    // 1. 单播： 数据只能只能绑定单个的订阅者(消费者）
    // 2. 多播： 管道能绑定多个订阅
    // 3. 重播： 管道能重放元素，是否给后来的订阅越发送之前的数据
    public static void unicast() {

        // 1. 单播
        Sinks.Many<Object> many = Sinks.many().unicast()
                .onBackpressureBuffer(new LinkedBlockingQueue<>(5));
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                many.tryEmitNext("a - " + i);
            }
        }).start();

        // 订阅
        many.asFlux().subscribe(System.out::println);
        // 这里如果使用了多个订阅者，就会报错, (unicast) 只允许单个订阅者
        many.asFlux().subscribe(System.out::println);
    }

    public static void multiCase() {
        // 2. 多播
        Sinks.Many<Object> many = Sinks.many().multicast()
                .onBackpressureBuffer();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                many.tryEmitNext("a - " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        many.asFlux().subscribe(item -> System.out.println("v1: " + item));
        new Thread(() -> {
            try {
                // 让第二个消费者等待一会才加入订阅
                Thread.sleep(5000);
                // 默认的订阅在加入的时候开始后续的消费
                many.asFlux().subscribe(item -> System.out.println("v2: " + item));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

    }


    public static void replay() {
        // 3. 重播
        Sinks.many().replay();
        Sinks.Many<Object> many = Sinks.many().replay()
                 .limit(5);  // 最近的 5 个
                // .latest(); // 最近的一个
                // .all(2);   // 所有，就是从头开始

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                many.tryEmitNext("a - " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        many.asFlux().subscribe(item -> System.out.println("v1: " + item));
        new Thread(() -> {
            try {
                Thread.sleep(8000);
                // 默认的订阅在加入的时候开始后续的消费
                many.asFlux().subscribe(item -> System.out.println("v2: " + item));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    /**
     * 元素的缓存
     */
    public static void cache() {

        Flux<Integer> flux = Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1))
                // 指定缓存的数量
                // 默认缓存了所有数据，就是多个消费者是都可以消费到所有数据的
                .cache(2);

        flux.subscribe();

        new Thread(() -> {
            try {
                Thread.sleep(10000);
                flux.subscribe(System.out::println);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    /**
     * 阻塞API, 有些场景里面，我们会发现就是需要阻塞获取到所有元素再开始处理，
     * 这时候需要用到这种阻塞式 API，它也是一种类型的订阅者，BlockingMonoSubscriber
     */
    public static void block() {
        List<String> block = Flux.range(1, 10)
                .map(e -> e + "-item")
                .collectList()
                .block();
        System.out.println(block);
    }


    /**
     * 并行流处理
     */
    public static void parallelFlux() {

        // 百万数据进行分批多线程处理
        Flux.range(1, 100000)
                // 以缓冲区的方式，每100 个元素进行分组分发
                .buffer(100)
                // 修改为并行流
                .parallel()
                // 指定运行的线程池
                .runOn(Schedulers.newParallel("handle-"))
                .log()
                .flatMap(Flux::fromIterable)
                .collectSortedList(Integer::compareTo)
                .log()
                .subscribe();
    }
}
