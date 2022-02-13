package lab2.method;

import lab1.*;
import lab2.function.Function;

import java.util.Arrays;

import static utils.MatrixUtil.*;

public class FastestDescent extends AbstractGradientMethod {

    public FastestDescent(double EPS) {
        super(EPS);
        this.iter = 1;
    }

    @Override
    public double[] findMin(Function function, double[] x0) {
        this.iter = 1;
        double[] x = x0 ;
        while (true) {
            double[] grad = function.applyGrad(x);
            if (norm(grad) < EPS) {
                break;
            }
//            print(x, grad);
            x = subtract(x, multiply(grad, findAlpha(x, grad, function)));
            this.iter += 1;
        }
//        System.out.printf(
//                "–Fastest descent:\nIter : %d\nX : %s\nGrad : %s\n",
//                iter,
//                Arrays.toString(x),
//                Arrays.toString(function.applyGrad(x))
//        );
        return x;
    }

    private double findAlpha(double[] x, double[] grad, Function function) {
        java.util.function.Function<Double, Double> f = alpha -> function.apply(subtract(x, multiply(grad, alpha)));
        // Choose one-dimensional optimization method
        return new MethodCombinationOfBrent(f, 0.0, 10.0, EPS).getMinimalValue();
    }

    @Override
    protected String getName() {
        return "Fastest descent";
    }
}