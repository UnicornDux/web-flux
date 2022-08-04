package com.edu.funcTool.util;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ListUtil {


    /**
     * 基于Stream 流式操作封装的集合过滤函数
     * @param list
     * @param p
     * @return
     * @param <T>
     */

   public static <T> List<T> filter(List<T> list, Predicate<T> p) {
       return list.stream().filter(p).collect(Collectors.toList());
   }

    /**
     * 基于Stream 流式操作封装的集合转换函数
     * @param list
     * @param function
     * @return
     * @param <T>
     * @param <R>
     */
   public static <T, R> List<R> mapTo(List<T> list, Function<T,R> function){
       return list.stream().map(function).collect(Collectors.toList());
   }



   public static <U,T,R> List<R> findFirstOne(List<U> list, Function<U,T> function, Function<T,R> function1) {
       return null;
   }


}
