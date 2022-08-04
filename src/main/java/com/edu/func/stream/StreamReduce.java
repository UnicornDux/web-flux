package com.edu.func.stream;

import com.edu.model.Employee;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Java Steam Reduce 方法的使用
 * Reduce 方法共有三个参数
 *   + 初始值传入值;
 *   + 处理函数（param1:单次返回值,param2:本次传入值);
 *   + 合并器;
 *
 */
public class StreamReduce {

    public static void main(String[] args) {
        List<Integer> in = Arrays.asList(1,2,3,4);
        int sum = in.stream().reduce(0,(total,element)->{
            //System.out.println(total);
            return total+element;
        },Integer::sum);
        System.out.println(sum);

        Employee e1 = new Employee(45,9075,"M","Jams","linux");
        Employee e2 = new Employee(95,9045,"F","Curry","jobs");
        Employee e3 = new Employee(65,9035,"M","JaSon","lucy");
        Employee e4 = new Employee(47,9040,"M","Harry","kuc");
        Employee e5 = new Employee(35,9015,"F","Lody","lue");
        Employee e6 = new Employee(67,9068,"F","Kemb","python");
        Employee e7 = new Employee(85,9005,"M","Kity","matin");
        Employee e8 = new Employee(32,9049,"F","Lozy","unix");
        Employee e9 = new Employee(40,9047,"M","Json","ubuntu");

        List<Employee> emp = Arrays.asList(e1,e2,e3,e4,e5,e6,e7,e8);

        int esum = emp.stream().map(Employee::getAge).reduce(0,(total, e)->{
            System.out.println(total);
            return total+e;
        },Integer::sum);
        System.out.println(esum);
        System.out.println("===============>>");

        /* 可以使用并行流进行计算 */
        int psum = emp.parallelStream().reduce(0,(total,e)->{
            //System.out.println(total);
            return total+ e.getAge();
        },Integer::sum);
        System.out.println(psum);

        /* 进行数据转换 */
        List<Integer> list_num = Arrays.asList(1,2,3,8,5,7,5,6,8,9,8,0);
        List<String> map_list = list_num.stream().map(Object::toString).collect(Collectors.toList());
        System.out.println(map_list);
    }
}
