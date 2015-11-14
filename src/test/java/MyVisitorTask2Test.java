import com.github.javaparser.ASTHelper;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.stmt.BlockStmt;
import org.junit.Test;

import java.io.FileInputStream;

import static org.junit.Assert.*;

/**
 * Created by Vasiliy on 12.11.2015.
 */
public class MyVisitorTask2Test {

    @Test
    /*тест должен проверить что в исходный файл добавляется функция фиббоначи*/
    public void testVisit() throws Exception {

        FileInputStream fromFile = new FileInputStream("from.java");
        CompilationUnit cu = null;
        try {
            // parse the file
            cu = JavaParser.parse(fromFile);
            }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            fromFile.close();
        }

        new MyVisitorTask2().visit(cu,null);

        String result = cu.toString();

        boolean flag1 = result.contains("public int fib(int n)");
        boolean flag2 = result.contains("if (n == 1 || n == 2)");
        boolean flag3 = result.contains("return 1;");
        boolean flag4 = result.contains("else");
        boolean flag5 = result.contains("return fib(n - 1) + fib(n - 2);");

        assertTrue("После выполнения new MyVisitorTask2().visit  CompilationUnit должен начать содержать функцию fib",(flag1 && flag2 && flag3 && flag4 && flag5));
    }

    @Test
    /*тест должен проверить что альтернатиынй способ создания Node c описанием фугкции фиббоначи работает
    * как работает метод AddFibToClassD1() - он принимает узел ClassOrInterfaceDeclaration и с помощью ASTHelper последовательно
     * добавляет необходимые узлы. Как лего. Проверка заключется в том, что полученынй распарсенный узел должен быть равен распарсиванию
      * строчки с описанием метода fib*/
    public void testAddFibToClassD1() throws Exception {

        ClassOrInterfaceDeclaration type = new ClassOrInterfaceDeclaration();

        new MyVisitorTask2().addFibToClassD1(type);

        ClassOrInterfaceDeclaration type2 = new ClassOrInterfaceDeclaration();

        try {
            BlockStmt block = JavaParser.parseBlock("{\n" +
                    "        if (n==1 || n==2)\n" +
                    "            return 1;\n" +
                    "        else\n" +
                    "            return fib(n-1) + fib(n-2);\n" +
                    "    }");
            MethodDeclaration method = new MethodDeclaration(ModifierSet.PUBLIC, ASTHelper.INT_TYPE, "fib");
            //method.setModifiers(ModifierSet.addModifier(method.getModifiers(), ModifierSet.STATIC));
            ASTHelper.addMember(type2, method);

            // add a parameter to the method
            Parameter param = ASTHelper.createParameter(ASTHelper.INT_TYPE, "n");

            ASTHelper.addParameter(method, param);

            // add a body to the method
            method.setBody(block);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assertEquals("Альтернативный способ добавления fib, addFibToClassD1()", type2.getMembers().get(0), type.getMembers().get(0));

    }
}