package com.edu.flux.reactor;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import java.util.List;

public class BufferCache {

    public static void main(String[] args) {
        // bufferData();
        bufferUtilChangeData();
    }

    public static void bufferData() {
       // 在流的下方添加一个缓冲区，将数据一批一批传送给消费者，
       // 实现消费者的批处理效果
        Flux<List<Integer>> flux = Flux.range(1,10)
                .buffer(3);

        // flux.subscribe(System.out::println);

        flux.subscribe(new BaseSubscriber<List<Integer>>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                System.out.println("建立连接");
                // 这里的请求不是指示数据的数量，表示的是请求的次数
                // 如果 数据被 buffer 处理时候，每次请求将得到 buffer 大小的数据
                // 直到所有的数据消费完成(最后可能是一个不到 buffer size 的数组)
                request(1);
            }

            @Override
            protected void hookOnNext(List<Integer> value) {
                System.out.println(value);
            }
        });
    }


    public static void bufferUtilChangeData() {
        // 在流的下方添加一个缓冲区，将数据一批一批传送给消费者，
        // 实现消费者的批处理效果
        Flux<List<Integer>> flux = Flux.range(1,10)
                // 按照给定的判定条件，如果元素操作之后与前一个值相同就粘性到前一个组
                // 如果与前一次不同则划分到新组.
                .bufferUntilChanged(item -> item % 4);

        // flux.subscribe(System.out::println);

        flux.subscribe(new BaseSubscriber<List<Integer>>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                System.out.println("建立连接");
                // 这里的请求不是指示数据的数量，表示的是请求的次数
                // 如果 数据被 buffer 处理时候，每次请求将得到 buffer 大小的数据
                // 直到所有的数据消费完成(最后可能是一个不到 buffer size 的数组)
                request(1);
            }

            @Override
            protected void hookOnNext(List<Integer> value) {
                System.out.println(value);
            }
        });
    }
}
