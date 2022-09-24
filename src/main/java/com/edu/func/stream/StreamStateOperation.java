package com.edu.func.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * > 流处理中的无状态操作(输入一个元素，返回一个元素，前后元素在处理的过程中没有相关性)
 *
 *  |- filter
 *  |- map
 *  |- peek
 *  |- flatmap
 * ------------------------------------------------------------------------------
 * > 流处理中的有状态操作
 *
 *  - limit
 *  - skip
 *  - distinct
 *  - sorted
 * ------------------------------------------------------------------------------
 * > parallel() 并行处理
 *
 *  - 区分有状态与无状态操作的目的就行在并行处理的时候，这种有状态被打乱，导致结果不符合预期
 *  - 适用于处理 ArrayList, [], HashMap (容易进行任务拆分)
 *  - 不利于 文件读取处理(基础数据源容易发生变化)，
 */
public class StreamStateOperation {

    public static void main(String[] args) {

        List<String> strList = Arrays.asList("linux","macos","hmos", "windows");

        // 取出前多少个
        List<String> limit = strList.stream().limit(2).collect(Collectors.toList());
        System.out.println(limit);
        // 跳过多少个
        List<String> skip = strList.stream().skip(3).collect(Collectors.toList());
        System.out.println(skip);
        // 去除重复
        List<String> distinct = strList.stream().distinct().collect(Collectors.toList());
        System.out.println(distinct);

        List<String> sort = strList.stream().sorted().collect(Collectors.toList());
        System.out.println(sort);

        Map<String, Long> collect = strList
            .stream()
            .collect(
                Collectors.groupingBy(
                        e -> "system_" + e,
                        Collectors.counting()
                )
            );
        System.out.println(collect);
    }
}
