package com.edu.flux.introduce;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * JDK 9 中 java.util.concurrent.Flow 中也实现了 reactive steam 规范
 */
@Slf4j
public class FirstImpression {

    public static void main(String[] args) {
        //testReactive();
        testProcess();
        // 由于是完全的异步过程，需要主线程进行等待，才能看到完整的日志
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void testProcess(){
        // 构建一个发布者
        SubmissionPublisher publisher = new SubmissionPublisher();
        // 构建中间处理器
        FlowProcessor processor = new FlowProcessor();
        // 建立发布者与中间处理器的联系
        publisher.subscribe(processor);
        // 构建真实的最终订阅者
        Flow.Subscriber subscriber = new Flow.Subscriber<String>() {
            private Flow.Subscription subscription;
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                log.info("Subscriber connect Processor successful");
                this.subscription = subscription;
                this.subscription.request(1);
            }
            @Override
            public void onNext(String item) {
                // 接收者接收到消息
                log.info("subscriber receiver message:>>"+item+"<<");
                this.subscription.request(1);
            }
            @Override
            public void onError(Throwable throwable) {
                log.info("subscriber receiver error");
                log.info(throwable.getMessage());
                this.subscription.cancel();
            }
            @Override
            public void onComplete() {
                log.info("subscriber receiver over");
            }
        };
        // 将最终订阅者与处理器之间建立关联
        processor.subscribe(subscriber);
        for (int i = 0; i < 10; i++) {
            publisher.submit("reactive stream Api");
        }
        publisher.close();
    }


    // 发布者与订阅者
    public  static  void testReactive(){
        log.info("{}", Flow.defaultBufferSize());
        // 创建一个发布者
        SubmissionPublisher submissionPublisher = new SubmissionPublisher();
        // 创建一个订阅者
        Flow.Subscriber  subscriber = new Flow.Subscriber() {
            Flow.Subscription subscription;
            @Override
            // 第一次初始化完成的时候，确定建立了关系
            public void onSubscribe(Flow.Subscription subscription) {
                log.info("建立订阅关系");
                this.subscription = subscription;
                subscription.request(1);
            }
            // 接收数据后的回调
            @Override
            public void onNext(Object item) {
                log.info("接收数据:" + item);
                // item 是接收传输数据的本体
                // 定义接收数据的数量
                subscription.request(10);
            }
            // 订阅中出现接收错误的时候触发
            @Override
            public void onError(Throwable throwable) {
                log.info("发生错误了");
            }
            // 接收消息完成之后的回调
            @Override
            public void onComplete() {
                log.info("接收到了");
            }
        };

        // 建立订阅关系
        submissionPublisher.subscribe(subscriber);
        // 循环发送
        for (int i = 0; i < 20; i++) {
            // 发送数据
            submissionPublisher.submit("hello reactive: <" + i + ">");
        }
        submissionPublisher.close();
    }
}
