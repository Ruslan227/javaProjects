package search;

import java.util.ArrayList;
import java.util.List;

public class BinarySearch {


    public static int bin1(int[] mas, int x, int l, int r) {
        int m = (l + r) / 2;
        if (l >= r)
            return r;
        else if (mas[m] <= x)
            return bin1(mas, x, l, m);
        else
            return bin1(mas, x, m + 1, r);
    }

    public static int bin(int[] mas, int x, int l, int r) {
        int m;
        while (l < r) {
            m = (l + r) / 2;
            if (mas[m] <= x) {
                r = m;
            } else
                l = m + 1;
        }
        return (r < mas.length) && (mas[r] == x) ? r : (-r - 1);
    }


    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(0);
            return;
        }
        int x = Integer.parseInt(args[0]);
        int[] mas = new int[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            mas[i - 1] = Integer.parseInt(args[i]);
        }
        System.out.println(bin(mas, x, 0, mas.length));
    }
}

