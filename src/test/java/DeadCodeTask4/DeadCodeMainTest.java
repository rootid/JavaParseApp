package DeadCodeTask4;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * Created by Vasiliy on 13.11.2015.
 */
public class DeadCodeMainTest {

    @Test
    /*тест должен проверить, что при подаче CompilationUnit в метод DeadCodeTask4(), метод уберет из CU
    * все объявления типа a=20, f = 100, ... и заменит переменные на соответсвующие значения, но только в классе D и в методе foo*/
    public void testDeadCodeTask4() throws Exception {

        /*входной текст file1:
        class D {
              public int foo(int e) {
         int a = 20,
                 b = (e > 0 ? e : 43) % 10,
                 c = a + b,
                 d = e > 0 ? c : c + a;
         int f = 112;

         if (d == 100) {
             return -1;
         } else if (d > 100) {
             return d;
         }
         return b + c;
         }
         }
     }*//*входной текст file2:
        class S {
              public int foo(int e) {
         int a = 20,
                 b = (e > 0 ? e : 43) % 10,
                 c = a + b,
                 d = e > 0 ? c : c + a;
         int f = 112;

         if (d == 100) {
             return -1;
         } else if (d > 100) {
             return d;
         }
         return b + c;
         }
         }
     }*/
        String strToCheck1 = "a = 20";
        String strToCheck2 = "f = 112";
        String strToCheck3 = "c = a + b";
        String strToCheck4 = "c = 20 + b";


        //file1 должен быть изменен
        FileInputStream file1 = new FileInputStream("forDeadCodeMainTest.java");
        CompilationUnit cu1 = getCompilationUnit(file1);
        DeadCodeMain.deadCodeTask4(cu1);
        String result = cu1.toString();
        assertFalse("Класс D со строчкой a = 20 теперь ее не содержит",result.contains(strToCheck1));
        assertFalse("Класс D со строчкой а = 112 теперь ее не содержит",result.contains(strToCheck2));
        assertTrue("Класс D со строчкой c = a + b теперь ее не содержит и содержит c = 20 + b", !result.contains(strToCheck3) && result.contains(strToCheck4));

        //file2 не должен быть изменен
        FileInputStream file2 = new FileInputStream("forDeadCodeMainTest1.java");
        CompilationUnit cu2 = getCompilationUnit(file2);
        DeadCodeMain.deadCodeTask4(cu2);
        String result2 = cu2.toString();
        assertTrue("Класс S со строчкой a = 20  ее  содержит", result2.contains(strToCheck1));
        assertTrue("Класс S со строчкой а = 112  ее  содержит", result2.contains(strToCheck2));
        assertTrue("Класс S со строчкой c = a + b  ее содержит и  содержит c = 20 + b", !result.contains(strToCheck3) && result.contains(strToCheck4));

    }

    public static CompilationUnit getCompilationUnit(InputStream in) {
        try {
            CompilationUnit cu;
            try {
                // parse the file
                cu = JavaParser.parse(in);
                return cu;
            } finally {
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}