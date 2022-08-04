package com.edu.funcTool.presenthandle;

import java.util.function.Consumer;

@FunctionalInterface
public interface PresentOrElseHandle<T extends Object> {

    /**
     * ----------------------------------------------------------------------------------
     * 联合使用Java8 中的 {@link Consumer} 和 {@link Runnable} 接口, 构建一个已下功能
     * 的函数接口：
     *     1、对象不为空的时候执行消费操作
     *     2、对象为空的时候执行其他的操作
     * @param action 用于对象存在时的处理逻辑
     * @param emptyAction 用于对象不存在时的处理逻辑
     * ----------------------------------------------------------------------------------
     */ 
    void presentOrElseHandle(Consumer<? super T>action, Runnable emptyAction);

}
