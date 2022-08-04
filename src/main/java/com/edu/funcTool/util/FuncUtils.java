package com.edu.funcTool.util;

import com.edu.funcTool.banchhandle.BranchHandle;
import com.edu.funcTool.exceptionhandle.ThrowExceptionHandle;
import com.edu.funcTool.presenthandle.PresentOrElseHandle;

/**
 * -------------------------------------------
 * 定义了函数接口怎么使用:
 * 
 * 1、 作为方法的参数；
 * 2、作为方法的返回值
 * -------------------------------------------
 */ 

public class FuncUtils {

    /**
     * 将是否抛出异常的接口类作为返回值进行返回
     * @param isThrow 是否抛出异常
     * @return com.edu.funcinterface.exceptionhandle.ThrowExceptionHandle
     */
    public static ThrowExceptionHandle isTrue(boolean isThrow) {
        return (errorMessage) -> {
            if (isThrow) {
                throw new RuntimeException(errorMessage);
            }
        };
    }

    /**
     * ------------------------------------------------------------
     *  分支判断处理器函数：根据传入的条件的计算结果决定使用哪一个
     *      1、成功处理器
     *      2、失败处理器
     * ------------------------------------------------------------
     */ 
    public static BranchHandle isTrueOrFalse(boolean b){
        return (trueHandle, falseHandle) -> {
            if (b) {
                trueHandle.run();
            }else{
                falseHandle.run();
            }
        };
    }

    /**
     * ------------------------------------------------------------
     * 将调用侧参数做为消费的参数，根据参数是否为 null 
     * 进行不同的回调函数处理
     */ 
    public static <T> PresentOrElseHandle<T> presentOrElseHandle(T str){
        return (consumer, runnable) -> {
            if (str == null){
                runnable.run();
            }else{
                consumer.accept(str);
            }
        };
    }

}
