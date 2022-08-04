package com.edu.oscmd;

import java.io.BufferedReader;


/*
 *
 * Java 程序调用系统中的命令行工具的方式:
 * -------------------------------------------------------
 * 1. 借助 Runtime 实现系统命令调用的功能
 * 2. 调用后可以获取到处理的结果，并且可以获取到命令的输出
 * 3. 可以获取到命令的错误信息
 */

public class InvokeProcess {
    public static void main(String[] args) {
        try {
            // 获取执行系统命令的对象
            Runtime rt = Runtime.getRuntime();
            // 执行命令
            Process p = rt.exec("ipconfig");
            // --------------------------------------------------------------------------------

            // Java 8 中只提供了字节流的输出，需要自己转换为字符流进行处理, 由于window在中文地区使用的命令行编码是 `GBK`,
            // 但是Java 编码使用的是 `UTF-8` 所以接收字节流转换为字符流的时候需要进行字符集的转换.
            // InputStreamReader inputStreamReader = new InputStreamReader(p.getInputStream(), "GBK");
            // BufferedReader bufferedReader = new BufferedReader(inputStreamReader);


            // java 17 中已经给我们封装好了字符缓冲流, 已经做好了字符集编码转换的处理, 直接使用即可
            BufferedReader bufferedReader = p.inputReader();


            String str = "";
            while ((str = bufferedReader.readLine()) != null) {
                System.out.println(str);
            }

            // --------------------------------------------------------------------------------
            // 等待命令行程序执行并返回结果, 正常的 c 或 c++ 实现的程序, 都会返回会对应的程序执行的结果
            // 程序执行成功返回 0，返回其他数字表示不同的程序执行异常
            int exitCode = p.waitFor();
            System.out.println("exitCode: " + exitCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
