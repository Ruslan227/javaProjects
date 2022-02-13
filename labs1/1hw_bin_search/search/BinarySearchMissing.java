package search;

public class BinarySearchMissing {
//    ] mas.length == n
//  inv: ∀ i ∈ [1...n-1] : mas[i-1] >= mas[i]  ∧ mas[] == const
//  pre:  inv ∧ l >= 0 ∧ r < n ∧ l <= r ∧ mas[l] >= x > mas[r]
//  post: ] ∃ i : mas[i] == x => return min(∀ i ∈ [0...n-1]) : mas[i] == x
//    else return (-i - 1) при i ∈ [1...n-1] : mas[i] <= x ∧ mas[i-1] > x (if x > mas[0] => return -1)
    public static int bin1(int[] mas, int x, int l, int r) {
        int m;
        if (l >= r) {
//   l >= r ∧ r < n ∧ mas[r] == x => return r
//   l >= r ∧ r < n ∧ mas[r] ≠ x => return - r - 1
            return (r < mas.length) && (mas[r] == x) ? r : (-r - 1);
        } else {
// if l < r => (mas[(l+r)/2] <= x) ? (return bin1(mas, x, l, (l+r)/2)) : (return bin1(mas, x, (l+r)/2+1, r))
            m = (l + r) / 2;
            if (mas[m] <= x) {
// inv ∧ r' = m
                return bin1(mas, x, l, m);
            } else {
// inv ∧ l' = m + 1
                return bin1(mas, x, m + 1, r);
            }
        }
    }


    //    ] mas.length == n
//  inv: ∀ i ∈ [1...n-1] : mas[i-1] >= mas[i] ∧ mas[] == const
    //  pre:  l == 0 ∧ r == n - 1 ∧ inv
    //  post: ] ∃ i : mas[i] == x => return min(∀ i ∈ [0...n-1]) : mas[i] == x
//    else return (-i - 1) при i ∈ [1...n-1] : mas[i] <= x ∧ mas[i-1] > x (if x > mas[0] => return -1)
    public static int bin(int[] mas, int x, int l, int r) {
        int m;
//  inv ∧ l == 0 ∧ r == n - 1
//
        while (l < r) {
//    l < r ∧ inv
            m = (l + r) / 2;
//      r' = m
            if (mas[m] <= x) {
                r = m;
            } else
//      l' = m + 1
                l = m + 1;
        }
        return (r < mas.length) && (mas[r] == x) ? r : (-r - 1);
//        ] ∃ i : mas[i] == x => return min(∀ i ∈ [0...n-1]) : mas[i] == x
//    else return (-i - 1) при i ∈ [1...n-1] : mas[i] <= x ∧ mas[i-1] > x (if x > mas[0] => return -1)
    }

    
// inv: ∀ i ∈ [1...n] : args[i]' == args[i] ∧ mas[i]' == mas[i] ∧ args[] == const
// pre: args[0...n] ∈ Set(int) ∧ ∀ i ∈ [1...n] : args[i-1] >= args[i]
// post: return (int)
//    args.length < 2 => return -1
//     ∃ i : (int)args[i] == x => return min(∀ i ∈ [1...n]) : (int)args[i] == x
//    else return (-i - 1) при i ∈ [1...n] : (int)args[i] <= x ∧ (int)args[i-1] > x (if x > (int)args[0] => return -1)
    public static void main(String[] args) {
//  mas.length == args.length - 1 ∧ ∀ i ∈ [1...n] : mas[i-1] >= mas[i]
        if (args.length < 2) {
            System.out.println(-1);
            return;
        }
//        x ∈ Set(int)
        int x = Integer.parseInt(args[0]);
//        inv
        int[] mas = new int[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            mas[i - 1] = Integer.parseInt(args[i]);
        }

        System.out.println(bin1(mas, x, 0, mas.length));
    }
}
