package com.edu.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.function.Predicate;

@Data
@NoArgsConstructor
public class Employee {

    private Integer age;
    private Integer id;
    private String gender;
    private String firstName;
    private String lastName;

    private Long strid;

    public Employee(Integer age, Integer id, String gender, String firstName, String lastName) {
        this.age = age;
        this.id = id;
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /* 将需要经常使用的条件变成谓词逻辑 */
    public static Predicate<Employee> ageGreater56 = x->x.getAge()>56;

    public static Predicate<Employee> genderM = x -> x.getGender().equals("M");
}
