package com.edu.flux.introduce;


import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * 中间处理器需要继承原始的发布者，实现Flow 的 关于中间处理器的接口
 */
public class FlowProcessor extends SubmissionPublisher<String>
        implements Flow.Processor<String,String> {

    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        System.out.println("publisher connect processor successful");
        this.subscription = subscription;
        this.subscription.request(1);
    }

    @Override
    public void onNext(String item) {
        // 中间处理器收到消息，进行处理
        System.out.println("process receiver message:<<" + item + ">>" );
        // 中间处理过程，处理完成之后要传送给订阅者
        this.submit(item.toUpperCase());
        // 背压实现的核心
        this.subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("process receive error");
        System.out.println(throwable.getMessage());
        this.subscription.cancel();
    }

    @Override
    public void onComplete() {
        System.out.println("Processor receive over");
    }
}
