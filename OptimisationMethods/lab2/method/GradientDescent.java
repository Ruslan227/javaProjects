package lab2.method;

import lab2.function.Function;

import java.util.Arrays;

import static utils.MatrixUtil.*;


public class GradientDescent extends AbstractGradientMethod {

    private double alpha;

    public GradientDescent(double alpha, double EPS) {
        super(EPS);
        this.iter = 1;
        this.alpha = alpha;
    }

    @Override
    public double[] findMin(Function function, double[] x0) {
        this.iter = 1;
        double[] prevX = x0, nextX;
        double prevFunc = function.apply(prevX), nextFunc;
        while (true) {
            double[] grad = function.applyGrad(prevX);
            if (norm(grad) < EPS || alpha < EPS) {
                break;
            }
            nextX = subtract(prevX, multiply(grad, alpha));
            nextFunc = function.apply(nextX);
            if (prevFunc > nextFunc) {
                prevX = nextX;
                prevFunc = nextFunc;
//                print(prevX, grad);
            } else {
                alpha /= 2;
            }
            this.iter += 1;
        }
//        System.out.printf(
//                "–Gradient descent:\nIter : %d\nX : %s\nGrad : %s\n",
//                iter,
//                Arrays.toString(prevX),
//                Arrays.toString(function.applyGrad(prevX))
//        );
        return prevX;
    }

    @Override
    protected String getName() {
        return "Gradient descent";
    }
}