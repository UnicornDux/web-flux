package com.edu.func.lambda;

/**
 * java 8 新特性，可以理解为ES6中的箭头函数--类似
 *  ()->{}
 *
 * > 当某个接口只有一个对应的需要实现的的方法的时候，这就是一个函数式接口，当我们去调用这样的接口的时候就不需要
 * 构建实现类来实现这样的方法，而是省略掉这个实现类，直接在调用接口内的方法时实现内部唯一的方法就可以了。
 *
 * > 因为 lambda 表达式需要一个目标类型，这个目标类型必须是一个函数式接口。
 *
 * -- 常见的例如Runnable接口内部的run()方法。
 *
 */

public class Lambda {
    // 打印接口
    interface Printer{
        // 打印方法
        void print(String content);
    }
    // 打印的方法
    public void printSomething(String content,Printer printer){
        printer.print(content);
    }

    /* 利用上述类封装一个打印功能 */
    public static void main(String[] args) {

        // 例如Runnable接口这样的匿名内部类
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("我是匿名内部类Runnable接口的实现");
            }
        }).start();

        //==>
        new Thread(()->{
            System.out.println("我是lambda-run方法的实现体");
        }).start();

        Lambda l = new Lambda();
        String hello = "hello World!";
        //----------------------------------------------
        /*
        Printer printer = new Printer() {
            @Override
            public void print(String content) {
                System.out.println(content);
            }
        };
        l.printSomething(hello,printer);
        */
        //----------------------------------------------
        /* 上述功能使用lambda实现上述功能

            Printer printer = (String val) -> {
                System.out.println(val);
            };
        */
        //可以省掉参数类型---------------------------------
        /* 由于接口内部只有一个待实现的方法，所以接口内方法参数类型可通过类型推导方式得到，可以省略不写
            Printer printer = (val) -> {
                System.out.println(val);
            };
        */
        // 只有一个参数的时候可以省略括号，没有参数可以使用一个空括号代替
        /*
            Printer printer = val -> {
                System.out.println(val);
            };
        */
        // 当处理的函数体只有一行的时候，{} 也可以省略
        /*
            Printer printer = val -> System.out.println(val);
        */
        // 最终将代码简化为下面的形式

        //l.printSomething(hello,val-> System.out.println(val));

        //-----------------------------------------------------
        // 官方推荐在某些地方可以使用方法引用代替lambda表达式
        l.printSomething(hello, System.out::println);
    }
}
