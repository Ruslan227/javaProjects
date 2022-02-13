package lab1;

import java.util.function.Function;

/**
 * Created by Danil Lyskin at 15:12 25.02.2021
 */

public class MethodDichotomy extends OneDimensionalSearch {


    public MethodDichotomy(Function<Double, Double> function, double leftBorder, double rightBorder, double EPS) {
        super(function, leftBorder, rightBorder, EPS);
    }

    @Override
    public double getMinimalValue() {
        return solve();
    }

    /**
     * calculate the result for 0.2𝑥𝑙𝑔(𝑥) + (𝑥 − 2.3)^2
     * @return 1 if result > 0, 0 if |result - 0| < eps, -1 if result < 0 {@link Integer}
     */
    private int check(double x1, double x2) {
        double res1 = function.apply(x1);
        double res2 = function.apply(x2);

        if (res2 > res1) {
            return 1;
        }
        return -1;
    }

    /**
     * method dichotomy
     * @return x which is answer {@link Double}
     */
    public double solve() {
        int iter = 1;
        double le = leftBorder;
        double ri = rightBorder;
        double del = EPS * 1e-2;
        while (ri - le > EPS) {
            double x1 = (le + ri - del) / 2;
            double x2 = (le + ri + del) / 2;

            if (check(x1, x2) == 1) {
                ri = x2;
            } else {
                le = x1;
            }
            iter++;
        }
        return (le + ri) / 2;
    }


}
