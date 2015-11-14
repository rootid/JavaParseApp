import org.junit.Test;

import static org.junit.Assert.*;

public class MainClassTest  {

    @Test
    public void testMain() throws Exception {

        //главный тест - конечный код выдает те же результаты что и исходный
        /*конечный код записан в TestingChallenge.java. копипаст из result.java

        D obj = new D();
        Will be printed: 38
        System.out.println(obj.foo(99));

        Will be printed: 116, 120, 328, 166, 832, 14, 136, 1042, 1690, 658, 176, 18
        System.out.println(obj.baz());*/

        D obj = new D();
        int resultFoo = obj.foo(99);
        String resultBaz = obj.baz();

        assertEquals("Метод foo(99) класса D из условия задания должен быть равен 38",38, resultFoo);
        assertEquals("Метод baz() класса D из условия должен выдавать строку 116, 120, 328, 166, 832, 14, 136, 1042, 1690, 658, 176, 18", "116, 120, 328, 166, 832, 14, 136, 1042, 1690, 658, 176, 18", resultBaz);

    }

    //todo create a test for GetCompilationUnit
}