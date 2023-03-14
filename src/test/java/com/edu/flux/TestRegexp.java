package com.edu.flux;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegexp {


    @Test
    public void testRegexp() {
        String str = "hello world,hello java,hello python";
        b(str);
    }

    public static void b( String str) {
        Pattern pattern = Pattern.compile("hello");

        Matcher matcher = pattern.matcher(str);

        if (matcher.find()) {
            System.out.println(matcher.group());
            System.out.println(matcher.end());
            System.out.println(matcher.start());
            str = str.substring(matcher.end());
            b(str);
        }
    }
    @Test
    void testRegex(){
        String name = "金华市金东区光南路OCC10-GJ-POS0600";
        Pattern pattern = Pattern.compile("(.*?)(\\d+)$");
        Matcher matcher = pattern.matcher(name);

        if (matcher.find()) {
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
            System.out.println(Integer.parseInt(matcher.group(2)));
        }
    }
}
