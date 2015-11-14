import java.util.ArrayList;
import java.util.List;

interface A {

    int foo(int e);
}

interface B {

    String bar(int el);
}

interface C extends A, B {

    String baz();

    String baz(int n);
}

class D<T> implements C {

    private int a = 2;

     D() {
    }

     D(int b) {
        this.a = 2 * (b > 0 ? b : 1);
    }

    public int foo(int e) {
        int b = (e > 0 ? e : 43) % 10, c = 20 + b, d = e > 0 ? c : c + 20;
        if (d == 100) {
            return -1;
        } else if (d > 100) {
            return d;
        }
        return b + c;
    }

    public int foo() {
        return foo(0);
    }

    public String bar(int el) {
        return String.valueOf(el * this.a);
    }

    public String baz(int n) {
        int[] c = { fib(10) + 3, fib(10) + 5, fib(12) + 20, fib(10) + 28, fib(14) + 39, fib(5) + 2, n > 0 ? n : fib(10) + 13, fib(14) + 144, fib(15) + 235, fib(13) + 96, fib(10) + 33, fib(6) + 1 };
        List<String> ret = new ArrayList<String>();
        for (int el : c) {
            ret.add(bar(el));
        }
        return String.join(", ", ret);
    }

    public String baz() {
        return baz(0);
    }

    public int fib(int n) {
        if (n == 1 || n == 2)
            return 1;
        else
            return fib(n - 1) + fib(n - 2);
    }
}

public class TestingChallenge<T> {

    public static void main(String... args) {
        D<?> obj = new D<Object>();
        // Will be printed: 38
        System.out.println(obj.foo(99));
        // Will be printed: 116, 120, 328, 166, 832, 14, 136, 1042, 1690, 658, 176, 18
        System.out.println(obj.baz());
    }
}
