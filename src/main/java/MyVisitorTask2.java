import com.github.javaparser.ASTHelper;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Vasiliy on 10.11.2015.
 * 2. Добавить новый метод `fib` для класса `D`, в задачи которого будет входить рекурсивное вычисление n-го числа ряда Фибоначчи, где `n`-передается в качестве значения единственного аргумента;
 *
 * визитер для нахождения класса D в исходном коде и исполнения задания #2
 */
class MyVisitorTask2 extends VoidVisitorAdapter
{
    @Override
    public void visit(ClassOrInterfaceDeclaration n, Object args)
    {
        if(n.getName().equals("D") )
        {

            //второй способ реализации задания 2 - с помощью распарсивания блока
            try {
                BlockStmt block = JavaParser.parseBlock("{\n" +
                        "        if (n==1 || n==2)\n" +
                        "            return 1;\n" +
                        "        else\n" +
                        "            return fib(n-1) + fib(n-2);\n" +
                        "    }");
                MethodDeclaration method = new MethodDeclaration(ModifierSet.PUBLIC, ASTHelper.INT_TYPE, "fib");
                //method.setModifiers(ModifierSet.addModifier(method.getModifiers(), ModifierSet.STATIC));
                ASTHelper.addMember(n, method);

                // add a parameter to the method
                Parameter param = ASTHelper.createParameter(ASTHelper.INT_TYPE, "n");

                ASTHelper.addParameter(method, param);

                // add a body to the method
                method.setBody(block);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

    }
    //метод, выполняющий задание №2. реализован данным способом для самообразования.

    public void addFibToClassD1(ClassOrInterfaceDeclaration n)
    {
        MethodDeclaration method = new MethodDeclaration(ModifierSet.PUBLIC, ASTHelper.INT_TYPE, "fib");
        ASTHelper.addMember(n, method);

        // add a parameter to the method
        Parameter param = ASTHelper.createParameter(ASTHelper.INT_TYPE, "n");

        ASTHelper.addParameter(method, param);

        // add a body to the method
        BlockStmt block = new BlockStmt();
        method.setBody(block);

        //define string "if ..."
        //define string "(n == 1 || n == 2)" condition
        BinaryExpr left = new BinaryExpr(new NameExpr("n"), new IntegerLiteralExpr("1"), BinaryExpr.Operator.equals );
        BinaryExpr right = new BinaryExpr(new NameExpr("n"), new IntegerLiteralExpr("2"), BinaryExpr.Operator.equals);
        BinaryExpr condition = new BinaryExpr(left, right, BinaryExpr.Operator.or );

        //define first return, thenStmt -  "return 1"
        ReturnStmt thenStmt = new ReturnStmt(new IntegerLiteralExpr("1"));

        //define second return, elseStmt - "return fib(n - 1) + fib(n - 2)"
        List<Expression> listR = new LinkedList<Expression>();
        listR.add(new BinaryExpr(new NameExpr("n"), new IntegerLiteralExpr("1"), BinaryExpr.Operator.minus));
        MethodCallExpr leftR = new MethodCallExpr(null,"fib", listR);
        List<Expression> listRR = new LinkedList<Expression>();
        listRR.add(new BinaryExpr(new NameExpr("n"),new IntegerLiteralExpr("2"), BinaryExpr.Operator.minus));
        MethodCallExpr rightR = new MethodCallExpr(null, "fib", listRR);
        ReturnStmt elseStmt = new ReturnStmt(new BinaryExpr(leftR,rightR, BinaryExpr.Operator.plus));

        IfStmt ifStmt = new IfStmt(condition,thenStmt,elseStmt);
        //add new IfStmt to Block
        ASTHelper.addStmt(block, ifStmt);
    }
}