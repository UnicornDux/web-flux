package com.edu.func.stream;

import com.edu.model.Employee;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Stream 中有map方法进行数据映射处理，
 * + map（）对数据进行逐个的处理，方法针对字面型的存储数据会返回一份新的数据，但是如果操作的变量是引用型的数据，
 *   会直接将源数据改变，引用该数据源得到的数据都将发生改变。
 * + flatMap（）对数据的处理方式可以替代常规的多层的for循环，使得高层级的数据结构也能展开，会将数据通过管道流
 *   进行合并处理
 */
public class StreamMap {

    public static void main(String[] args) {

        // 根据数据的形式进行遍历处理
        Employee e1 = new Employee(45, 9075, "M", "Jams", "linux");
        Employee e2 = new Employee(95, 9045, "F", "Curry", "jobs");
        Employee e3 = new Employee(65, 9035, "M", "JaSon", "lucy");
        Employee e4 = new Employee(47, 9040, "M", "Harry", "kuc");
        Employee e5 = new Employee(35, 9015, "F", "Lody", "lue");
        Employee e6 = new Employee(67, 9068, "F", "Kemb", "python");
        Employee e7 = new Employee(85, 9005, "M", "Kity", "matin");
        Employee e8 = new Employee(32, 9049, "F", "Lozy", "unix");
        Employee e9 = new Employee(40, 9047, "M", "Json", "ubuntu");

        List<Employee> employees = Arrays.asList(e1,e2,e3,e4,e5,e6,e7,e8,e9);

        List<Employee> empes = employees.stream()
                .peek(e->{
                    // 需要进行复杂处理的时候可以使用多行代码
                    e.setAge(e.getAge()+1);
                    e.setGender(e.getGender().equals("M")? "Male" : "Female");
                }).collect(Collectors.toList());
        System.out.println(employees);
        System.out.println(empes);
        System.out.println("------------------------------------------");


        /* 将数组转换为名称长度并进行打印 */
        employees.stream()
                .mapToInt(e->e.getLastName().length())
                .forEach(System.out::print);
        /* 将数组中的元素的年龄都增加一岁，性别补全 */
        System.out.println();
        System.out.println("------------------");
        List<Employee> emps = employees.stream()
                .map(e->{
                    // 需要进行复杂处理的时候可以使用多行代码
                    e.setAge(e.getAge()+1);
                    e.setGender(e.getGender().equals("M")? "Male" : "Female");
                    return e;
                }).collect(Collectors.toList());
        System.out.println(emps);
        System.out.println(employees);
        /* 针对以上的情景，可以使用peek代替map，操作集合里面本来存在的引用形式的变量无需进行返回,可以将peek看作一种特殊的map */
//        List<Employee> empes = employees.stream()
//                .peek(e->{
//                    // 需要进行复杂处理的时候可以使用多行代码
//                    e.setAge(e.getAge()+1);
//                    e.setGender(e.getGender().equals("M")? "Male" : "Female");
//                }).collect(Collectors.toList());
//        System.out.println(empes);

        /* 针对一元的结合map可以进行处理，但是针对二元甚至是层级更高的数据结构，map就无法进行处理，
           这时就要使用到FlatMap了，可以映射理解为常规的多层for 处理的过程
        */
        List<String> words = Arrays.asList("hello","world");
        // 得到的是两个数组的引用地址，无法得到数组里面的遍历
        words.stream().map(e->e.split("")).forEach(System.out::println);
        // 得到两个数组
        words.stream().map(e->Arrays.asList(e.split(""))).forEach(System.out::println);
        // 得到两个数组转换成的数据流地址
        words.stream().map(e->Arrays.stream(e.split(""))).forEach(System.out::println);
        // 全部展开，成为单个的字母
        words.stream().flatMap(e->Arrays.stream(e.split(""))).forEach(System.out::println);
        List<String> chars = words.stream()
                .flatMap(e->Arrays.stream(e.split("")))
                .collect(Collectors.toList());
        System.out.println(chars);
    }
}
