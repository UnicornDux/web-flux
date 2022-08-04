package com.edu.func.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Java 8 新特性
 * stream API
 *  + 数组
 *  + 集合
 *  + 文件IO
 *  +
 *  转化为流的操作源，进行过滤，排序，数据转化，
 *  转化完成后再进行转换为其他的数据格式
 */
public class StreamDemo {

    public static void main(String[] args) {

        /* 集合转化为流过程 */
        List<String> nameStr = Arrays.asList("Linux", "Microsoft", "Mac", "Ubuntu", "JVM");
        List<String> nameL = nameStr.stream()
                .filter(s -> s.startsWith("M")) // 过滤函数，留下符合规则的对象
                .map(String::toUpperCase)       // 调用String 的toUpperCase() 函数
                .sorted()                       // 进行排序，可以自定义排序的规则
                .collect(Collectors.toList());  // 将结果进行收集后转化为数组
        nameL.stream().map(e -> e.toUpperCase(Locale.ROOT)).limit(1).forEach(System.out::println);
        System.out.println(nameL);

        /* 数组转化流 */
        String[] system = {"Linux", "Microsoft", "Mac", "Ubuntu", "JVM"};
        List<String> sys = Stream.of(system)
                .filter(s -> s.startsWith("M")) // 过滤函数，留下符合规则的对象
                .map(String::toUpperCase)       // 调用String 的toUpperCase() 函数
                .sorted()                       // 进行排序，可以自定义排序的规则
                .collect(Collectors.toList());  // 将结果进行收集后转化为数组
        System.out.println(sys);

        /* 集合转换为流对象 */
        Set<String> se = new HashSet<String>(nameStr);
        List<String> set = se.stream()
                .filter(s -> s.startsWith("M")) // 过滤函数，留下符合规则的对象
                .map(String::toUpperCase)       // 调用String 的toUpperCase() 函数
                .sorted()                       // 进行排序，可以自定义排序的规则
                .collect(Collectors.toList());  // 将结果进行收集后转化为数组
        System.out.println(sys);

        /* 文件转化为流进行处理 */

        try {
            Stream<String> str = Files.lines(Paths.get("src/main/property/file.txt"));
            List<String> sos = str.filter(s->s.startsWith("M"))
                    .map(String::toUpperCase)
                    .sorted()
                    .collect(Collectors.toList());
            System.out.println("==========================>>>>>>>>>>");
            System.out.println(sos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{

            Stream<String> str = Files.lines(Paths.get("src/main/property/log.json"));
            List<String> logList = str
                    .filter(e ->{
                        return e.startsWith("\"aPort") || e.startsWith("\"zPort");
                    }).map(e -> {
                        List<String> list = b(e,new ArrayList<>());
                        //return String.join(",", list);
                        return list.toString();
                    }).collect(Collectors.toList());
            System.out.println(logList);
            System.out.println(logList.size());
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static List<String> b( String str, List<String> k) {
        Pattern pattern = Pattern.compile("\"E\\w{23}\"");
        Matcher matcher = pattern.matcher(str);

        if (matcher.find()) {
            k.add(matcher.group().replace("\"", "'"));
            //System.out.println(matcher.group());
            str = str.substring(matcher.end());
            b(str,k);
        }
        return k;
    }
}
