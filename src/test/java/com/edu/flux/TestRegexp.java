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


}
