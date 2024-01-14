package com.edu.flux.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 核心库里面的一些 API
 */
public class Api {

    public static void main(String[] args) {
        // filterApi();
        // flatMap();
        // concat();
        // transform();
        // empty();
        // merge();
        zip();
    }

    /**
     * zip 操作的时候，会以较小元素个数的流为准, 形成一个一个的元组结构 Tuple
     * 元素个数较多无法形成元组的会被丢弃
     */
    public static void zip(){
        Flux.just(1, 2, 4).zipWith(
              Flux.just("a", "b", "c", "d")
        )
        // 这里可以对元组中的值取出，使用元组中的序号做为 get 方法
        // 这里的元组个数是可以支持 2-8 个
        .map(tuple -> tuple.getT1() + tuple.getT2())
        .log()
        .subscribe();
    }

    public static void merge(){
        // merge 会以时间为基线，根据多个流中元素到达的先后顺序来完成元素的订阅
        Flux.merge(
                Flux.just(1, 2, 3).delayElements(Duration.ofSeconds(1)),
                Flux.just("a", "b").delayElements(Duration.ofMillis(1500)),
                Flux.just("haha", "hehe", "xixi").delayElements(Duration.ofMillis(2000))
        )
        .log()
        .subscribe();

        //
        // mergeWith 与 merge 类似，只是调用的位置不太一样
        Flux.just(1, 2, 3, 4).mergeWith(
            Flux.just(10, 20, 30).delayElements(Duration.ofMillis(100))
        )
        .log()
        .subscribe();


        // mergeSequential
        // 这个合是根据流的顺序，不是根据元素出现的顺序
        Flux.mergeSequential(
                Flux.just(1, 10, 20).delayElements(Duration.ofSeconds(1)),
                Flux.just(3, 5, 7).delayElements(Duration.ofSeconds(2))
        )
        .log()
        .subscribe();
    }


    public static void empty() {
        // Mono.just(null) 表示有一个空元素
        // Mono.empty() 表示没有元素的空流

        // defaultIfEmpty 表示上游没有元素的时候可以使用默认的元素
        Mono.empty().defaultIfEmpty("x").subscribe();
        // switchIfEmpty 表示上游没有元素时转换到一个备用的流上面
        Mono.empty().switchIfEmpty(Mono.just("k")).subscribe();
    }



    public static void transform(){

        AtomicInteger atom = new AtomicInteger(0);
        Flux<String> flux = Flux.just("a", "b", "c")
                // 这里两种 API 的差异是
                // transform 会对多个消费者共享外部变量的状态
                // transformDeferred 不会在多个消费者之间共享外部变量的状态
                //.transform(values -> {
                .transformDeferred(values -> {
                    if (atom.incrementAndGet() == 1) {
                        return values.map(String::toUpperCase);
                    } else {
                        return values;
                    }
                });
        flux.subscribe(v -> System.out.println("订阅者1: " + v));
        flux.subscribe(v -> System.out.println("订阅者2: " + v));
    }


    // 连接流
    public static void concat(){
        Flux.concat(
                Flux.fromArray(new String[]{"hello", "world"}),
                Flux.just("reactor", "stream")
        )
        .log()
        .subscribe();

        // concatMap
        // 消费一个上游流的元素，映射到一个新的流, 再将这些新的流连接起来
        Flux.just(1, 3)
                .concatMap(s -> Flux.just(s + "->a", 1, 10, 20), 10)
                .log()
                .subscribe();


        // concatWith
        // 连接两个相同类型的流数据形成一个新的数据流
        Flux.just(1, 2, 5, 8, 9)
                .concatWith(Flux.just(10, 20))
                .log()
                .subscribe();

    }

    public static void flatMap(){
        Flux.just("Alex JoSon", "Alis maria")
                .flatMap(v-> {
                    //相当于每一个元素又化成一个新的流,
                    // 将流扁平化的操作
                    return Flux.fromArray(v.split(" "));
                }).subscribe();
    }

    public static void filterApi(){
       Flux.just(1,2,3,4)
               .filter(item -> item % 2 == 0)
               .log()
               .subscribe();
    }

    // 直接获取第一个，后续丢弃
    public static void next() {
      Integer val = Flux.just(1, 3, 6)
        .next()
        .block();
      System.out.println(val);
    }

}
