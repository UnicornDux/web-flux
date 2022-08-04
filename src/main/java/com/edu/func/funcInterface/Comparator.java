package com.edu.func.funcInterface;

/**
 * ----------------------------------------------------------------------
 *  >> 函数式接口
 * ----------------------------------------------------------------------
 *
 * - 函数接口内有且仅有一个抽象方法，
 * - 允许静态（public static）非抽象方法
 * - 允许 default 修饰的非抽象方法，默认实现--（java8以及之后）
 * - 允许java.lang.Object 类中的 public 方法
 * - @FunctionalInterface  (可选，加上了可以在编译期进行语法检查)
 *
 */

@FunctionalInterface
public interface Comparator {

    String print(String val);

    static int sum(int a, int b){
        return a + b;
    }

    default String isAll(){
        return "";
    }
}
