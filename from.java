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

    D() {}

    D(int b){
        this.a = 2 * (b > 0 ? b : 1);
    }

    public int foo(int e) {
        int a = 20,
                b = (e > 0 ? e : 43) % 10,
                c = a + b,
                d = e > 0 ? c : c + a;
        int f = 100;

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
        int[] c = { 58, 60, 164, 83, 416, 7, n > 0 ? n : 68, 521, 845, 329, 88, 9 };

        List<String> ret = new ArrayList<String>();
        for (int el : c) {
            ret.add(bar(el));
        }

        return String.join(", ", ret);
    }

    public String baz() {
        return baz(0);
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