package com.edu.func.funcInterface;
import java.util.Random;
import java.util.function.*;
import java.util.stream.Stream;


public class JdkFunctionDemo{

    public static void main(String[] args) {

        // supplier 只有输出，没有输出
        Supplier<String> supplier = ()-> "this is supplier";
        System.out.println(supplier.get());

        // Consumer 只有输入,没有输出
        Consumer<String> consumer = (e)-> System.out.println("this is demo for consumer:" + e);
        consumer.accept("unicorn");

        // function 一个输入一个输出
        Function<Integer,String> function = Object::toString;
        System.out.println(function.apply(9));

        // 输入输出相同的时候
        UnaryOperator<Integer> unaryOperator = e -> e * e;
        System.out.println(unaryOperator.apply(9));

        // BiFunction<B, T, R>
        BiFunction<Integer,Integer,String> biFunction = (i,j)->{
            return i + "*" + j +"=" + (i * j);
        };
        System.out.println(biFunction.apply(9,9));

        // 生成 1-10 序列
        Stream.iterate(1, e -> e + 1)
                .limit(10).forEach(System.out::println);

        // 生成随机数
        Stream.generate(()->new Random().nextInt(10))
                .limit(10).forEach(System.out::println);
    }
}
