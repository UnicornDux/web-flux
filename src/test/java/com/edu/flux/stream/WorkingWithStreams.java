package com.edu.flux.stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class WorkingWithStreams {

    @Test
    @Disabled
    void steams() {
        List<String> names = List.of("Amigoscode", "Alex", "Zara");
        Stream<String> stream = names.stream();

        Stream<String> namesStream = Stream.of("Amigoscode", "Alex", "Zara");

        long count = stream
                .limit(2).map(null).sorted(null).dropWhile(null)
                .count();

        String[] namesArray = {};
        Arrays.stream(namesArray);
    }

    /**
     * 自定义的基本类型排序
     */
    @Test
    void testCustomSortOrderInBaseType() {
        int[] arr = { 1, 5, 7, 4, 9, -1, 0, 3 };
        arr = IntStream.of(arr)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .mapToInt(Integer::intValue)
                .toArray();
    }

    @Test
    void findTopKey() {
        int[] arr = { 1, 6, 8, 1, 5, 9, 10, 2, 9, 2, 7, 4, 7, 4, 3 };
        int k = 5;

        Arrays.stream(arr)
                .boxed()
                .collect(Collectors.toMap(e -> e, e -> 1, Integer::sum))
                .entrySet()
                .stream()
                .sorted((m1, m2) -> m2.getValue() - m1.getValue())
                .limit(k)
                .mapToInt(Map.Entry::getKey)
                .toArray();
    }

}
