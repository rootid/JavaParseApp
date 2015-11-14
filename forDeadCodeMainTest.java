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
