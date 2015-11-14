package DeadCodeTask4;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;

import java.io.InputStream;
import java.util.Hashtable;

/**
 * 4. Произвести оптимизацию кода метода `foo` класса `D` (удаление мертвого кода);
 * оптиммизация на данный момент:
 * удаляется объявления типа "int а=20", "b=132"; затем значения переменных подставляются заместо самих переменных в коде
 * Created by Vasiliy on 09.11.2015.
 */
public class DeadCodeMain {

    public static void  deadCodeTask4(CompilationUnit cu)
    {
        //буду использовать Hashtable (не HashMap потому что нужна потокобезопасность) для того, чтобы сохранять значения
        // узлов, которые должны быть удалены, а их значение пдставлено в выражения
        //например, здесь должно сохраниться значение a=20

        //визитеры будут просматривать все VariableDeclarator, т.е. объявление переменных, находить такие случаи как a=20,
        //сохранять сведения об этом в Hashtable,
        //и удалять узлы, которые содержат такие значения - DeadCodeFirst.MyVisitorDeadCodeFirst().visit

        //затем подставлять значения из hashtable в код - DeadCodeSecond().visit
        Hashtable<String, Node> map = new Hashtable<String, Node>();
        try {
            new DeadCodeFirst.MyVisitorDeadCodeFirst().visit(cu, map);
            new DeadCodeSecond().visit(cu,map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

