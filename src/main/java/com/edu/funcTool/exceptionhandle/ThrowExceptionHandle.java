package com.edu.funcTool.exceptionhandle;

@FunctionalInterface
public interface ThrowExceptionHandle{

    /**
     * -------------------------------------------------------------------------------
     *  利用 java8 中提供的 consumer 接口，定义一个只有接收参数，没有返回值的接口函数
     *  @param message 抛出的异常信息
     *  @return void   
     *
     */ 
    void throwMessage(String message);

}
