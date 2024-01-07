package com.edu.flux.reactor;

import lombok.SneakyThrows;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * 流需要被消费，没有消费就没有用，消费的方式就是订阅
 *
 */


public class ReactorCore {

    @SneakyThrows
    public static void main(String[] args) {

        // mono();
        // flux();
        // generator();
        creator();
        // logger();
        // disposable();

        System.in.read();
    }

    public static void mono() {
        /**
         * Mono is 0 or 1, 产生一个数据流
         */
        Mono.empty().subscribe(System.out::println);
        Mono.just("hello").subscribe(System.out::println);
    }

    /**
     * Flux is 0-n, 产生一个数据流
     *  > 有多样化的方式来产生一个数据流
     *
     */
    public static void flux(){
        // 产生数据列表的
        Flux.just(1,2,3,4,5).subscribe(System.out::println);
        // 按照迭代器产生数据
        Flux.fromIterable(Arrays.asList("a","b","c","d","e")).subscribe(System.out::println);
        // 从数组产生
        Flux.fromArray(new String[]{"hello","reactor"}).subscribe(System.out::println);
        // 从一个范围产生
        Flux.range(100,120).subscribe(System.out::print);
        // 从数据流中产生
        Flux.fromStream(Stream.of(1,3,5,7,9)).subscribe(System.out::println);

        // 按照一定的时间间隔产生数据
        Flux.interval(Duration.ofSeconds(1)).subscribe(System.out::println);

    }


    /**
     * 单线程产生数据流
     */
    // sink ： 接收器，水槽
    public static void generator() {
        Flux.generate(
            // 初始值
            ()->0,
            // 生产数据流的逻辑 (相当是一个循环, 我们需要根据 state 来控制什么时候终止数据的生成)
            (state,sink)->{
                // 生成数据
                sink.next("2 * " + state + "=" + 2 * state);
                if (state == 9) {
                    // 终止生成数据
                    sink.complete();
                }
                // 返回新的 state， 会在下一次循环中作为参数传入到 (state, sink)
                return state+1;
            }
        ).subscribe(System.out::println);
    }


    /**
     * 多线程产生流
     */
    public static void creator (){
        Flux<Object> flux = Flux.create(fluxSink -> {
            for (int i = 0; i < 20; i++) {
                int finalI = i;
                new Thread(() -> {
                    OnLineListener onLineListener = new OnLineListener(fluxSink);
                    onLineListener.online("用户" + finalI);
                }).start();
            }
        });

        flux.subscribe(System.out::println);
    }

    static class OnLineListener {
        FluxSink<Object> fluxSink;

        OnLineListener(FluxSink<Object> fluxSink) {
           this.fluxSink = fluxSink;
        }
        public void online(Object name){
            System.out.println( name + ": 上线");
            fluxSink.next(name);
        }
    }



    /**
     * 查看流数据消费的日志 API
     */

    public static void logger(){
        Flux.range(1, 8)
                // 显示的是 range 产生的流的消费数据的过陈
                // .log()
                .filter(i -> i > 3)
                // 显示的 filter 产生的流的消费数据的过程
                .log()
                .subscribe(System.out::println);
    }


    /**
     * 看生产者与消费者的区别
     */
    public static void disposable() {

        // 我们生产一个流，并对这个流进行各种处理，只要没有订阅
        // 它始终都是一个生产者
        Flux<Integer> flux = Flux.range(1, 1000)
                .delayElements(Duration.ofSeconds(5))
                .log()
                .map(i -> i * 10);


        // 一旦一个生产者流被订阅消费了
        // disposable 是可结束，可取消的
        Disposable subscribe = flux.subscribe();

        new Thread(() -> {
            try {
                Thread.sleep(5000);
                // 取消订阅
                subscribe.dispose();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
