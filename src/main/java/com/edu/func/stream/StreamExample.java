package com.edu.func.stream;

import java.util.stream.Stream;

public class StreamExample {

    public static void main(String[] args) {
        /**
         * -------------------------------------------------------
         * 流处理的中间操作
         *  map, flatmap, peek
         *  filter, limit, sorted
         *  distinct
         * -------------------------------------------------------
         * 流终止操作
         *  非短路操作:
         *      - reduce, foreach, Collect, Max/Min, Count
         *  短路操作:
         *      - FindFirst/FindAny, AnyMatch/AllMatch/NoneMatch
         * -------------------------------------------------------
         *
         * 给定如下一组字符串数组, --->> 输出 b e l o
         *
         * */

        String[] str = {"react","","spring","bo_le","windows","webFlux","bo_le"};

        Stream.of(str)  // 数组转化为流对象
                // 流处理操作 中间操作,可以是(1-n)次
                .filter(i-> !i.isEmpty())  // 去除空元素
                .distinct()  // 去重
                .sorted()    // 排序
                .limit(1)    // 取出第一个
                .map(e->e.replace("_","")) // 去除 _
                .flatMap(e->Stream.of(e.split("")))   //  将字符数组转换为字符流
                .sorted()  // 字符排序
                // 流处理的时候必须要有一个流终止操作,并且有且只能有一个流终止操作
                .forEach(System.out::println); // 字符数组输出
    }
}
