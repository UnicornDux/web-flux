package com.edu.func.collector;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ListCollector {

    public static void main(String[] args) {

        List<String> nameStr = Arrays.asList("Linux", "Microsoft", "Mac", "Ubuntu", "JVM");
        collectorToList(nameStr);
        collectorToUnModifiableList(nameStr);
        collectorToSet(nameStr);
        collectorToJoin(nameStr);
        collectorToMap(nameStr);
    }

    /**
     * 将 List 收敛为一个新的 list
     * ----------------------------------------------------------------------
     * Collectors.toList()
     */
    public static void collectorToList(List<String> nameStr) {
        List<String> nameL = nameStr.stream()
                .sorted()                       // 进行排序，可以自定义排序的规则
                .collect(Collectors.toList());  // 将结果进行收集后转化为数组
        System.out.println(nameL);
    }
    
    /**
     * 将 List 收敛为一个不可变的 List
     * ----------------------------------------------------------------------
     * Collectors.toUnmodifiableList()
     */
    public static void collectorToUnModifiableList(List<String> nameStr) {
        List<String> collect = nameStr.stream().collect(Collectors.toUnmodifiableList());
        System.out.println(collect);
    }

    /**
     * 将 List 收敛为一个新的 set
     * ----------------------------------------------------------------------
     * Collectors.toSet()
     */
    public static void collectorToSet(List<String> nameStr) {
        Set<String> collect = nameStr.stream()
                .collect(Collectors.toSet());
        System.out.println(collect);
    }

    /**
     * 将 List 收敛为一个 String
     * ----------------------------------------------------------------------
     * Collectors.toMap()
     */
    public static void collectorToJoin(List<String> nameStr) {
        String joinStr = nameStr.stream().collect(Collectors.joining("|"));
        System.out.println(joinStr);
    }

    /**
     * 将 List 收敛为一个 Map
     * ----------------------------------------------------------------------
     * Collectors.toMap()
     */
    public static void collectorToMap(List<String> nameStr) {
        Map<String, String> map = nameStr.stream()
                .collect(Collectors.toMap(e -> "_" + e, String::toString));
        map.forEach((k, v) -> System.out.println(k + ":" + v));
    }

}
