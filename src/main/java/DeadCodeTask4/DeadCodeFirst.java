package DeadCodeTask4;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.visitor.ModifierVisitorAdapter;

import java.util.Hashtable;

/**
 * Created by Vasiliy on 09.11.2015.
 */
public class DeadCodeFirst {

    //буду использовать Hashtable для того, чтобы сохранять значения узлов, которые должны быть заменены
    //например, здесь должно сохраниться значение a=20

    //визитер будет просматривать все VariableDeclarator, т.е. объявление переменных, находить такие случаи как a=20,
    //сохранять сведения об этом в Hashtable,объявленном в DeadCodeMain,
    //и удалять узлы, которые содержат такие значения
    //при удалении VariableDeclarator в случае "int a = 20" останется "int ",поэтому нужно начать просмотр с общего высокого уровня и удалить такие узлы
    //Более обший уровень для VariableDeclarator это VariableDeclarationExpr
    //есть еще возможность, что на конце будет ";" ("а = 20;") и тогда после удаленй, описанных выше, останется только ";", что равносильно
    // пустому ExpressionStmt. Поэтому просмотр начнется с еще более выского уровня - ExpressionStmt. и в случае пустого - удалять
    static class MyVisitorDeadCodeFirst extends ModifierVisitorAdapter{

        //нужно убедиться, что просматривается класс D
        @Override
        public Node visit (ClassOrInterfaceDeclaration classDeclaration, Object args)
        {
            if (classDeclaration.getName().equals("D"))
                super.visit(classDeclaration, args);
            return classDeclaration;
        }

        //нужно убедиться, что просматривается метод foo
        @Override
        public Node visit (MethodDeclaration methodDeclaration, Object args)
        {
            if (methodDeclaration.getName().equals("foo"))
                super.visit(methodDeclaration, args);
            return methodDeclaration;
        }

        /*начинаем просмотр с ExpressionStmt и, если он пуст, обнуляем. Где-то в глубинах Javaparser
        * в одном из итераторов, который обходит AST, проихойдет удаление пустого ExpressionStmt
        * этот же принцип сработает в остальных просмотрщиках узкого уровня*/
        @Override
        public Node visit (ExpressionStmt stmt, Object args)
        {
            super.visit(stmt,args);
            if (stmt.getExpression()==null)
            {
                return null;
            }
            return stmt;
        }

        /*Если в массиве VariableDeclarationExpr нет элементов, то обнуляем ссылку от родителя на массив, читай, возвращаем пустоту*/
        @Override
        public Node visit (VariableDeclarationExpr declarationExpr, Object args)
        {
            //просматриваем все объявления
            //в каждом объявлении может быть несколько инициализаций, просматриваем все инициализации в каждом объявлении
            super.visit(declarationExpr, args);

            //если в данном объвлении не осталось ничего кроме типа, например "int ", то ее нужно удалить
            if (declarationExpr.getVars().isEmpty()) {
                return null;
            }

            return declarationExpr;
        }

        /*просматривает непосредственно объявления*/
        @Override
        public Node visit(VariableDeclarator declarator, Object args)
        {
//            находим все объявления переменных, которые инициализированы целым литералом, т.е. те у которых за знаком равно идет константа
            //нужно соблюдать иерархию просмотра. нужно попасть в деклараторы ТОЛЬКО функции foo, но по иерархии деклараторы
            //стоят на равне с объявлениями методов, поэтому нужно проверить, что тип родителя декларатора - метод
            if (declarator.getInit() instanceof IntegerLiteralExpr && declarator.getParentNode() instanceof VariableDeclarationExpr)
            {
                String valueString = ((IntegerLiteralExpr) declarator.getInit()).getValue();
                int valueInteger = Integer.parseInt(valueString);
                ((Hashtable)args).put(declarator.getId().getName(), valueInteger);

                return null;
            }
            return declarator;
        }
    }
}

