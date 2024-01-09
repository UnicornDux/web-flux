package com.edu.flux.reactor;

import lombok.SneakyThrows;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定流的线程调度方式
 */
public class SchedulerThread {

    @SneakyThrows
    public static void main(String[] args) {

        // api();

        time();

        System.in.read();
    }


    // 改变线程调度的 api
    public static void api(){
        Flux.range(1, 10)
                // .publishOn(Schedulers.immediate())
                // .publishOn(Schedulers.single())
                .publishOn(Schedulers.parallel())
                .log()
                .subscribeOn(Schedulers.boundedElastic())
                .subscribeOn(Schedulers.fromExecutor(new ThreadPoolExecutor(
                                5,
                                10,
                                60,
                                TimeUnit.SECONDS,
                                new LinkedBlockingDeque<>(100),
                                new ThreadPoolExecutor.CallerRunsPolicy()
                        ))
                ).subscribe();
    }


    // 线程改变的时机
    // 默认发布者用的线程就是订阅者的线程（因为是订阅动作才会触发整个流的运行）
    public static void time(){

         Scheduler scheduler = Schedulers.newParallel("parallel-thread", 4);

        // publishOn 出现的位置决定了什么时候开始应用线程池
        // 每一个流的中间操作，都是消费了一个流，产生一个新的流
        Flux<String> flux = Flux.range(1,10)
                .map(i -> i + 10)
                .log()
                .publishOn(scheduler)
                .map(item -> "value" + item)
                .log();


        // 消费订阅数据
        new Thread(() -> flux.subscribe(System.out::println)).start();
    }
}
