import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import static org.junit.Assert.*;


public class MyVisitorTask3Test {

    @Test
    /**
     * тест проверяет, что MyVisitorTask3Test.visit() заменяет элементы массива C на вызовы fib по указанному правилу
     * тест смотрит, что в исходном файле from.java есть строка int[] c = { 58, 60, 164, 83, 416, 7, n > 0 ? n : 68, 521, 845, 329, 88, 9 };
     * и что в конечном файле result.java есть строка int[] c = { fib(10) + 3, fib(10) + 5, fib(12) + 20, fib(10) + 28, fib(14) + 39, fib(5) + 2, n > 0 ? n : fib(10) + 13, fib(14) + 144, fib(15) + 235, fib(13) + 96, fib(10) + 33, fib(6) + 1 };
     */
    public void testVisit() throws Exception {


        String fromString = "        int[] c = { 58, 60, 164, 83, 416, 7, n > 0 ? n : 68, 521, 845, 329, 88, 9 };";
        String resultString = "        int[] c = { fib(10) + 3, fib(10) + 5, fib(12) + 20, fib(10) + 28, fib(14) + 39, fib(5) + 2, n > 0 ? n : fib(10) + 13, fib(14) + 144, fib(15) + 235, fib(13) + 96, fib(10) + 33, fib(6) + 1 };";

        boolean flagFrom = isContain(fromString,"from.java");
        boolean flagResult = isContain(resultString,"result.java");

        assertTrue("файл from.java содержит необходимую строку",flagFrom);
        assertTrue("файл result.java содержит необходимую строку",flagResult);

    }

    public boolean isContain (String stringTemplate, String stringFile) throws Exception
    {
        Scanner scanner = new Scanner(new File(stringFile));

        String currentLine;

        while(scanner.hasNext())
        {
            currentLine = scanner.nextLine();
            if(currentLine.contains(stringTemplate))
            {
                return true;
            }
        }

        return false;
    }

    @Test
    /*тест проверяет как отрабатывает функция findArgumentForFib(int n), которая возвращает число p, при этом fib(p)
    максимально приближенно к числу n с левой стороный. в случае, когда n - число Фиббоначи, то findArgumentForFib и fib(n-1) - обратные функции */
    public void testFindArgumentForFib() throws Exception {
        /*ряд ФИбоннччи: 1ое число - 1, 2ое - 1, 3е- 2, 4е-3, 5 - 5, 6 - 8, 7 - 13, 8 - 21, 9 - 34, 10 - 55, 11 - 89, 12 - 144, 13- 233, */

        assertEquals("findArgumentForFib(3) равно 4",4-1, new MyVisitorTask3().findArgumentForFib(3));
        assertEquals("findArgumentForFib(34) равно 9", 9 - 1, new MyVisitorTask3().findArgumentForFib(34));
        assertEquals("findArgumentForFib(233) равно 13", 13 - 1, new MyVisitorTask3().findArgumentForFib(233));
        assertEquals("findArgumentForFib(89) равно 11", 11 - 1, new MyVisitorTask3().findArgumentForFib(89));
    }

    @Test
    /*проверка функции fib(n), которая возвращает n-ое чилсо фибоннчи */
    public void testFib() throws Exception {

        int[] fibArray = {1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233};

        for (int i = 1; i < 10 ; i++)
        {
            assertEquals("fib(" + i + ") = " + fibArray[i-1], fibArray[i-1], new MyVisitorTask3().fib(i));
        }

    }
}