package com.edu.func.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 介绍流是怎样生成的
 */
public class StreamCreate {

    public static void main(String[] args) {

        // 1. 从数组中产生
        System.out.println("1: ==========================>>>>>>>>>>");
        Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Stream.of(arr).forEach(System.out::println);

        // 2. 从集合中产生
        System.out.println("2: ==========================>>>>>>>>>>");
        Arrays.asList("Linux", "Microsoft", "Mac", "Ubuntu", "JVM")
                .stream()
                .forEach(System.out::println);

        // 3. 从字符串中产生
        System.out.println("3: ==========================>>>>>>>>>>");
        "Hello World".chars().forEach(System.out::println);

        // 4. 使用迭代器生成
        System.out.println("4: ==========================>>>>>>>>>>");
        Stream.iterate(1, e -> e + 1)
                .limit(10).forEach(System.out::println);

        // 5. 使用生成器生成
        System.out.println("5: ==========================>>>>>>>>>>");
        Stream.generate(() -> new Random().nextInt(10))
                .limit(10).forEach(System.out::println);

        // 6. 从文件中读取
        try (Stream<String> str = Files.lines(Paths.get("src/main/property/file.txt"))){
            System.out.println("6: ==========================>>>>>>>>>>");
            List<String> sos = str.filter(s -> s.startsWith("M"))
                    .map(String::toUpperCase)
                    .sorted()
                    .collect(Collectors.toList());
            System.out.println(sos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 连接两个流
        Stream.concat(Stream.of("hello"), Stream.of("world"))
                .forEach(System.out::println);

    }
}
