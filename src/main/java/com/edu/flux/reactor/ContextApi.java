package com.edu.flux.reactor;

import reactor.core.publisher.Flux;
import reactor.util.context.Context;

/**
 * 由于响应式编程中大部分的异步线程切换，操作过程运行的线程不是确定的，所以无法使用 ThreadLocal
 * 来解决线程上下文传递变量的目的，
 * > 提供的解决方案是使用 Context 上下文来传递上下文中定义的变量
 *
 * > 定义的上下文的变量是从 订阅者(下游） 往上游 (发布者）传递的，
 *   上游可以拿到最近一次的数据
 *
 */
public class ContextApi {

    // 实际使用 Context API 的时候不是所有的API 中斗支持传递上下文
    // 需要使用 带有 Contextual 后缀的 API 才能得到上下文数据
    public static void main(String[] args) {

        Flux.just(1, 2, 5)
                .transformDeferredContextual((val, context) -> {
                    System.out.println("val" + val);
                    System.out.println("context" + context);
                    return val.map(item -> context.get("prefix").toString() + item);
                })
                .contextWrite(Context.of("prefix", "api-"))
                .subscribe(System.out::println);

    }

}
