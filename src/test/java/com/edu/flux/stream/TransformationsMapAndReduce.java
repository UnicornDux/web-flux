package com.edu.flux.stream;

import com.edu.flux.mockdata.MockData;
import com.edu.model.Car;
import com.edu.model.Person;
import com.edu.model.PersonDTO;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransformationsMapAndReduce {

    @Test
    void yourFirstTransformationWithMap() throws IOException {
        List<Person> people = MockData.getPeople();

        Function<Person, PersonDTO> personPersonDTOFunction = person -> new PersonDTO(
                person.getId(),
                person.getFirstName(),
                person.getAge());

        List<PersonDTO> dtos = people.stream()
                .filter(person -> person.getAge() > 20)
                .map(PersonDTO::map)
                .collect(Collectors.toList());

        dtos.forEach(System.out::println);

    }

    @Test
    void mapToDoubleAndFindAverageCarPrice() throws IOException {
        List<Car> cars = MockData.getCars();
        double avg = cars.stream()
                .mapToDouble(Car::getPrice)
                .average()
                .orElse(0);
        System.out.println(avg);
    }

    @Test
    void testMapAndCount() {
        String[] arr = { "a", "c", "b", "a", "d", "c" };
        /*
         * // 循环计算的方法
         * Map<String, Integer> map = new HashMap<>();
         * for (String s : arr) {
         * map.put(s, map.getOrDefault(s, 0) + 1);
         * }
         */

        // streama 转化到 map 中 key, value 的转换操作
        Stream.of(arr)
                .collect(Collectors.toMap(k -> k, k -> 1, (p, c) -> p + c))
                .forEach((k, v) -> System.out.println(k + ":" + v));
    }

    @Test
    public void reduce() {
        int[] integers = { 1, 2, 3, 4, 99, 100, 121, 1302, 199 };
        int sum = Arrays.stream(integers).reduce(0, Integer::sum);
        int sub = Arrays.stream(integers).reduce(0, (a, b) -> a - b);
        System.out.println(sum);
        System.out.println(sub);
    }
}
