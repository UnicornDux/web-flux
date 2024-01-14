package com.edu.flux.reactor;

import org.checkerframework.checker.units.qual.A;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 错误处理
 */
public class ErrorHandle {


    public static void main(String[] args) {
        // errorReturn();
        // errorResume();
        // errorMap();
        // doFinally();
        // errorContinue();
        errorStop();
    }

    public static void errorReturn () {
        Flux.just(1, 2, 0, 4)
                .map(x -> "100 / " + x + " = " + 100 /x)
                // 这里可以返回一个自定义的返回值，出现错误的时候就返回这个值
                // 这里被处理之后，下方的错误感知回调将不会调用，同时流会提前结束
                // .onErrorReturn("err")

                // 还有可以处理指定的异常, 只有在遇到这个异常的时候才会处理
                .onErrorReturn(ArithmeticException.class, "divide zero")
                .subscribe(
                        item -> System.out.println("success:" + item),
                        err -> System.out.println("err: " + err.getMessage()),
                        () -> System.out.println("complete")
                );

    }

    // 异常之后返回返回一个新的流, 让流可以切换到另外一个
    //
    public static void errorResume() {

        Flux.just(1, 2, 0, 4)
                .map(x -> "100 / " + x + " = " + 100 /x)

                // 上一个流到这结束，会接下来消费这个新的流
                // 订阅者是无法感知到这个错误的，无法触发错误的回调
                // 1.
                // .onErrorResume((item) -> Flux.just("true", "false"))
                // 2.
                // .onErrorResume(err -> {
                //     if (err instanceof ArithmeticException) {
                //         return Mono.just("zero");
                //     }
                //     return Mono.just("RuntimeException");
                // })
                // 3.
                // .onErrorResume(err -> Flux.error(new RuntimeException("自定义异常")))
                .subscribe(
                        item -> System.out.println("success:" + item),
                        err -> System.out.println("err: " + err.getMessage()),
                        () -> System.out.println("complete")
                );
    }

    // 异常转换为自定义异常, 重新抛出，这样整个流会直接结束，
    // 如果没有后续的异常处理，消费者会接收到异常，并进行回调
    public static void errorMap() {

        Flux.just(1, 2, 0, 4)
                .map(x -> "100 / " + x + " = " + 100 /x)
                // 如果是要抛出自定义异常，推荐使用这种方式
                .onErrorMap(err -> new RuntimeException("运行异常"))
                .subscribe(
                        item -> System.out.println("success:" + item),
                        err -> System.out.println("err: " + err.getMessage()),
                        () -> System.out.println("complete")
                );
    }


    // 在错误钩子里面做一些处理，例如做记录
    public static void onError() {
        Flux.just(1, 2, 0, 4)
                .map(x -> "100 / " + x + " = " + 100 /x)
                .doOnError( err -> {
                    // 这里可以处理一些附加的操作，例如记录到文件或者数据库
                    // 但是异常会继续往消费者侧传递
                    System.out.println("err: " + err.getMessage());
                })
                .subscribe(
                        item -> System.out.println("success:" + item),
                        err -> System.out.println("err: " + err.getMessage()),
                        () -> System.out.println("complete")
                );
    }


    // finally 块，无论流是正常还是异常操作都会触发
    public static void doFinally() {
        Flux.just(1, 2, 0, 4)
                .map(x -> "100 / " + x + " = " + 100 /x)
                .doFinally((item) -> {
                    System.out.println("结束了");
                })
                .subscribe(
                        item -> System.out.println("success:" + item),
                        err -> System.out.println("err: " + err.getMessage()),
                        () -> System.out.println("complete")
                );
    }

    // 异常元素跳过，后续元素继续处理
    public static void errorContinue() {
        Flux.just(1, 2, 0, 4)
                .map(x -> "100 / " + x + " = " + 100 /x)
                .onErrorContinue((err, val) -> {
                    // 这个记录以下处理哪一个元素出现了异常
                    System.out.println(val  + " handle error: " + err.getMessage());
                })
                .subscribe(
                        item -> System.out.println("success:" + item),
                        err -> System.out.println("err: " + err.getMessage()),
                        () -> System.out.println("complete")
                );
    }

    // 出错时停止, 这里是流源头的中断，所有订阅流的消费者都
    // 会停止
    public static void errorStop() {
        Flux.just(1, 2, 0, 4)
                .map(x -> "100 / " + x + " = " + 100 /x)
                .onErrorStop()
                .subscribe(
                        item -> System.out.println("success:" + item),
                        err -> System.out.println("err: " + err.getMessage()),
                        () -> System.out.println("complete")
                );
    }

}
