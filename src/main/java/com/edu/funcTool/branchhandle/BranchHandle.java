package com.edu.funcTool.branchhandle;

@FunctionalInterface
public interface BranchHandle {
    /**
     * ----------------------------------------------------------
     * 使用 Java 8 中 Runable 接口：没有输入，没有输出的模式
     * 构建分支处理逻辑
     * 
     * @param trueHandle  Runable 接口形式参数，用于处理true的分支逻辑
     * @param falseHandle Runable 接口形式参数，用于处理false的分支逻辑
     *                    ----------------------------------------------------------
     */
    void trueOrFalseHandle(Runnable trueHandle, Runnable falseHandle);

}
