package com.edu.flux.reactor;

import reactor.core.publisher.Flux;

public class ReactorDemo {

    public static void main(String[] args) {
        /**
         *
         *  将输入的一句话转化为一个排好序的字符序列
         *  Hello guys, I am bole welcome to normal school  jdk quick fox prizev
         *
         *  ==> abcdefghijklmnopqrstuvwxyz
         */

        String src = "Hello guys, I am bole welcome to normal school  jdk quick fox prizev";

        Flux.fromArray(src.split(" "))
                .flatMap(i-> Flux.fromArray(i.split("")))
                .map(String::toLowerCase)
                .distinct()
                .sort()
                .skip(2) // 去掉空格 / 逗号
                .subscribe(System.out::print);
    }
}
