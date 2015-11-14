import com.github.javaparser.ParseException;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;

import static com.github.javaparser.JavaParser.parseExpression;

/**
 * Created by Vasiliy on 10.11.2015.
 * 3. Заменить каждое из значений массива переменной `c` метода `baz` на вызов метода `fib`, где аргументом будет достаточное значение числа для получения заменяемого значения массива и возможного остатка;
 *
 * Визитер заходит в каждый метод, ищет и выполняет 3е задание
 */

class MyVisitorTask3 extends VoidVisitorAdapter {

    @Override
    public void visit(ReferenceType n, Object arg) {

        //нахожу вхождение искомого массива по уникальному параметру , строке "int[]"
        if (n.toString().equals("int[]"))
        {
            //беру ссылку на родителя этого узла, чтобы получить доступ к элементам массива
            VariableDeclarationExpr node = (VariableDeclarationExpr) n.getParentNode();
            //через структуру беру ссылку на массив
            List<Expression> values = ((ArrayInitializerExpr)node.getVars().get(0).getInit()).getValues();
            //провожу изменения согласно заданию для каждого элемента массива
            for (int i = 0; i < values.size(); i++)
            {
                Expression exp = values.get(i);
                Class clazz = exp.getClass();
                //если тип элемента массива - целое число
                if (clazz == IntegerLiteralExpr.class)
                {
                    //добрался до значения элемента массива. Поменял его на вызов функции, определяющей число
                    int valueExp = Integer.parseInt(((IntegerLiteralExpr) exp).getValue());
                    int argForFib = findArgumentForFib(valueExp);
                    int ostatok = valueExp - fib(argForFib);
                    Expression newExp;
                    try {
                        //должен заменить вхождение целого числа на строчку fib(argForFib) + ostatok
                        newExp = parseExpression("fib(" + argForFib + ")+" + ostatok);
                        values.set(i,newExp);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                //если тип элементам - выражение (для случая "n > 0 ? n : 68"
                if (clazz == ConditionalExpr.class)
                {
                    Expression conditionalExpr = ((ConditionalExpr) exp).getCondition();
                    Expression thenExpr = ((ConditionalExpr) exp).getThenExpr();
                    Expression elseExpr = ((ConditionalExpr) exp).getElseExpr();

                    if (thenExpr.getClass() == NameExpr.class) {
                        String nameThen = ((NameExpr)thenExpr).getName();
                        if (elseExpr.getClass() == IntegerLiteralExpr.class) {
                            int intElse = Integer.parseInt(((IntegerLiteralExpr) elseExpr).getValue());
                            try {
                                int argForFib = findArgumentForFib(intElse);
                                int ostatok = intElse - fib(argForFib);
                                String str = "fib(" + argForFib + ") +" + ostatok;
                                Expression newExp = parseExpression(conditionalExpr.toString() + "?" + nameThen + ":" + str );
                                values.set(i,newExp);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    //метод определяет какой аргумент должен быть у fib
    public int findArgumentForFib(int n)
    {
        int i=1;
        while( fib(i) < n )
            i++;
        return i-1;
    }

    public int fib(int n)
    {
        if (n==1 || n==2)
            return 1;
        else
            return fib(n-1) + fib(n-2);
    }
}