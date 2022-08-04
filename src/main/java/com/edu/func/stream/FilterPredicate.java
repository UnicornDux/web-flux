package com.edu.func.stream;

import com.edu.model.Employee;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Stream 的 Filter 谓词逻辑
 * ------------------------------------
 */
public class FilterPredicate {

    public static void main(String[] args) {

        Employee e1 = new Employee(45,9075,"M","Jams","linux");
        Employee e2 = new Employee(95,9045,"F","Curry","jobs");
        Employee e3 = new Employee(65,9035,"M","JaSon","lucy");
        Employee e4 = new Employee(47,9040,"M","Harry","kuc");
        Employee e5 = new Employee(35,9015,"F","Lody","lue");
        Employee e6 = new Employee(67,9068,"F","Kemb","python");
        Employee e7 = new Employee(85,9005,"M","Kity","matin");
        Employee e8 = new Employee(32,9049,"F","Lozy","unix");
        Employee e9 = new Employee(40,9047,"M","Json","ubuntu");


        List<Employee> employeeList = Arrays.asList(e1,e2,e3,e4,e5,e6,e7,e8);
        List<Employee> emps = employeeList.stream()
                .filter(e->e.getAge()>56 && e.getGender().equals("M"))
                .collect(Collectors.toList());

        System.out.println(emps);

        /* 如果filter的条件需要复用，可以将上述的条件变成一种属性，成为一种谓词逻辑 */
        List<Employee> employees = Arrays.asList(e1,e2,e3,e4,e5,e6,e7,e8,e9);
        List<Employee> empList = employees.stream()
                .filter(Employee.ageGreater56.and(Employee.genderM))
                .collect(Collectors.toList());
        System.out.println(empList);


        employeeList.stream().mapToLong(FilterPredicate::getAge).boxed()
                .filter(age->age != null)
                .forEach(System.out::println);

    }

    public static Long getAge(Employee e){
        return e.getStrid() == null ? 0L : e.getStrid();
    }
}
