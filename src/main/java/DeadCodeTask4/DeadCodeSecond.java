package DeadCodeTask4;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.visitor.ModifierVisitorAdapter;

import java.util.Hashtable;

/**
 * обходит код, ищет переменные из hashtable.keys и заменяет их на значения hashtable.values
 * Created by Vasiliy on 12.11.2015.
 */
public class DeadCodeSecond extends ModifierVisitorAdapter {
    
        //нужно убедиться, что просматривается метод foo
        @Override
        public Node visit (MethodDeclaration methodDeclaration, Object args)
        {
            if (methodDeclaration.getName().equals("foo"))
                super.visit(methodDeclaration, args);
            return methodDeclaration;
        }

        @Override
        public Node visit(NameExpr nameExpr, Object args)
        {
            Hashtable hashtable = (Hashtable)args;

            //если имя переменной содержится как ключ в карте, то сделать замену на выражение и вернуть.
            // При этом поменятеся тип узла на IntegerLiteralExpr
            if (hashtable.containsKey(nameExpr.getName()))
            {

                IntegerLiteralExpr newExpr = new IntegerLiteralExpr((hashtable.get(nameExpr.getName()).toString()));

                return newExpr;
            }

            return nameExpr;
        }
    }



