package com.edu.func.stream;

import com.edu.model.Employee;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Stream sort 进行排序，将排序进行得像SQl语言一样简洁（以集合中得对象为例）
 * + 利用本身提供得API中包含的排序得方法，默认使用的是升序的排序，
 * + 要想使用倒叙来排序，需要调用方法 reversed()
 * + reversed()方法作用范围是所有前面的排序规则，当进行多个字段的排序时，越靠前的字段的排序取决于
 *   后续 reversed()方法调用的次数,当调用次数为奇数次的时候是倒叙，为偶数次的时候是正序.
 */
public class StreamSorted {

    public static void main(String[] args) {

        /* 关于排序的复习 */

        List<String> Str  = Arrays.asList("Beijing","ShangHai","ShenZhen","HongKong");
        // 大小写不敏感的排序
        Str.sort(String.CASE_INSENSITIVE_ORDER);
        System.out.println(Str);
        // 自然顺序
        Str.sort(Comparator.naturalOrder());
        System.out.println(Str);
        //
        Str.stream().sorted(Comparator.naturalOrder());
        Str.forEach(System.out::println);

        // 倒叙
        Str.stream().sorted(Comparator.reverseOrder());
        System.out.println(Str);
        /*本次排序的测试数据*/
        Employee e1 = new Employee(45,9075,"M","Jams","linux");
        Employee e2 = new Employee(95,9045,"F","Curry","jobs");
        Employee e3 = new Employee(65,9035,"M","JaSon","lucy");
        Employee e4 = new Employee(47,9040,"M","Harry","kuc");
        Employee e5 = new Employee(35,9015,"F","Lody","lue");
        Employee e6 = new Employee(67,9068,"F","Kemb","python");
        Employee e7 = new Employee(85,9005,"M","Kity","matin");
        Employee e8 = new Employee(32,9049,"F","Lozy","unix");
        Employee e9 = new Employee(40,9047,"M","Json","ubuntu");
        // 转化为List
        List<Employee> employeeList = Arrays.asList(e1,e2,e3,e4,e5,e6,e7,e8);

        List<Employee> emps = employeeList.stream()
                .sorted(
                        // 多个字段进行排序，前面的字段先排，后面的字段在排
                        Comparator.comparing(Employee::getGender)
                                .reversed()  // 影响当前字段排序 （本次Gender应该是正序）
                                .thenComparingInt(Employee::getAge)
                                .reversed()  // 影响前面所有字段的排序 （本次Age是倒叙）
                )
                .collect(Collectors.toList());

        emps.forEach(System.out::println);

    }
}
