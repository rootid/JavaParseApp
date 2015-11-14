import DeadCodeTask4.DeadCodeMain;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Created by Vasiliy on 11.11.2015.
 */
public class MainClass {

    public static void main(String...args) throws Exception
    {
        //1. Построить AST для приведенного ниже скрипта (это можно сделать как встроенными средствами, так и сторонними) и произвести описанные ниже действия на его уровне;
        //скрипт я храню во внешнем файле from.java
        //для работы использую модуль JavaParser https://github.com/javaparser/javaparser

        FileInputStream fromFile = new FileInputStream("from.java");

        CompilationUnit cu = getCompilationUnit(fromFile);

        //2. Добавить новый метод `fib` для класса `D`, в задачи которого будет входить рекурсивное вычисление n-го числа ряда Фибоначчи, где `n`-передается в качестве значения единственного аргумента;
        //описание действий находиться в классе MyVisitorTask2.java
        new MyVisitorTask2().visit(cu, null);

        // 3. Заменить каждое из значений массива переменной `c` метода `baz` на вызов метода `fib`, где аргументом будет достаточное значение числа для получения заменяемого значения массива и возможного остатка;
        //описание действий находиться в классе MyVisitorTask3.java
        new MyVisitorTask3().visit(cu, null);

        // 4. Произвести оптимизацию кода метода `foo` класса `D` (удаление мертвого кода);
        //описание в DeadCodeTask4.DeadCodeMain

        DeadCodeMain.deadCodeTask4(cu);

        //5. Преобразовать результирующий AST обратно в Java-код;
        PrintWriter pw = new PrintWriter("result.java", "UTF-8");
        pw.write(cu.toString());
        pw.close();

        //6. юнит-тесты в папке test/java
    }
    //построение AST для кода из исходного файла
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
