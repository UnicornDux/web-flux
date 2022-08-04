package com.edu.flux;

import com.edu.funcTool.util.FuncUtils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FluxApplicationTests {

    @Test
    void contextLoads() {

    }

    @Test
    public void testThrowExceptionHandle(){
        FuncUtils.isTrue(false).throwMessage("异常抛出");
    }

    @Test
    public void testBranchHandle(){
        FuncUtils.isTrueOrFalse(false).trueOrFalseHandle(
            ()->{
                System.out.println("true");
            },
            ()->{
                System.out.println("false");
            }
        );
    }

    @Test
    public void testPresentOrElseHandle(){
        FuncUtils.presentOrElseHandle(null).presentOrElseHandle(
            (message)-> {
                System.out.println(message);
            },
            () -> {
                System.out.println("default");
            }
        );
    }
}
