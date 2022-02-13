package lab2;

import lab2.function.Function;
import lab2.function.SquareFunction;
import lab2.function.SquareFunctionDiagView;
import lab2.method.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static utils.MatrixUtil.*;

public class Main {

    private static void runAll() {
        int[] sizes = {10, 25, 50, 75, 100, 250, 500, 750, 1000, 2500, 5000, 7500, 10000};
        double[] conditions = new double[sizes.length];
        IntStream.range(0, sizes.length).forEach(i -> conditions[i] = Math.random() * 1000);
        Arrays.sort(conditions);
        System.out.println(Arrays.stream(sizes).mapToObj(Objects::toString).collect(Collectors.joining(" ")));
        System.out.println(Arrays.stream(conditions).mapToObj(d -> String.format("%.6f", d)).collect(Collectors.joining("\n")));
        for (double k : conditions) {
            for (int n : sizes) {
                run(n, k);
            }
            System.out.println();
        }
    }

    private static void run(int n, double k) {
        double[] A = generateDiagMatrix(n, k);
        double[] B = generateVector(n, k);
        double C = Math.random() * k;
        double[] x0 = generateVector(n);
        double eps = 1e-5, alpha = 10;
        Function f = new SquareFunctionDiagView(A, B, C);
        Method m1 = new GradientDescent(alpha, eps);
        Method m2 = new FastestDescent(eps);
        Method m3 = new ConjugateGradient(eps);
//        m1.findMin(f, x0);
//        System.out.print(m1.getIter() + " ");
//        m2.findMin(f, x0);
//        System.out.print(m2.getIter() + " ");
//        m3.findMin(f, x0);
//        System.out.print(m3.getIter() + " ");
    }

    private static void run() {
        double[][] A = {{128.0, 40.0}, {40.0, 128.0}};
        double[] B = {-10.0, 30.0};
        double C = 13.0;
        double[] x0 = {-1.0, -1.0};
        double eps = 1e-5, alpha = 0.5;
        Function f = new SquareFunction(A, B, C);
        Method method = new FastestDescent(eps);
        method.findMin(f, x0);
    }

    public static void main(String[] args) {
        run();
    }
}
