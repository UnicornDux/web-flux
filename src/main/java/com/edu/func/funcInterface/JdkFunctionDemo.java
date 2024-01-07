package com.edu.func.funcInterface;
import java.util.Random;
import java.util.function.*;
import java.util.stream.Stream;


// JDK 在包 java.util.function 包中定义了大量的常见的函数式接口类型
// 下面介绍一些常用的类型

public class JdkFunctionDemo{

    public static void main(String[] args) {

        supplier();
        consumer();
        function();
        unaryOperation();
        biFunction();
        predicate();

        // 生成 1-10 序列
        Stream.iterate(1, e -> e + 1)
                .limit(10).forEach(System.out::println);

        // 生成随机数
        Stream.generate(()->new Random().nextInt(10))
                .limit(10).forEach(System.out::println);
    }


    // predicate
    public static void predicate() {
        // 用于判断一个字符串是否为一个数字
        Predicate<String> predicate = (val) -> val.matches("-?\\d+(\\.\\d+)?");
        System.out.println(predicate.test("254535.3535"));
        System.out.println(predicate.test("254535"));
        System.out.println(predicate.test("254535.a3535"));
        System.out.println(predicate.test("0.3535a"));

    }





    // 两个输入类型，一个输出类型
    public static void biFunction() {
        // BiFunction<B, T, R>
        BiFunction<Integer,Integer,String> biFunction = (i,j)->{
            return i + "*" + j +"=" + (i * j);
        };
        System.out.println(biFunction.apply(9,9));
    }


    // 输入输出相同的时候
    public static void unaryOperation() {
        UnaryOperator<Integer> unaryOperator = e -> e * e;
        System.out.println(unaryOperator.apply(9));
    }


    // function 一个输入一个输出
    public static void function() {
        Function<Integer,String> function = Object::toString;
        System.out.println(function.apply(9));
    }



    // Consumer 只有输入,没有输出
    public static void consumer() {
        Consumer<String> consumer = (e)-> System.out.println("this is demo for consumer:" + e);
        consumer.accept("unicorn");
    }

    // supplier 只有输出，没有输出
    public static void supplier() {
        Supplier<String> supplier = ()-> "this is supplier";
        System.out.println(supplier.get());
    }
}
