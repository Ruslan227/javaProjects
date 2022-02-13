package lab1;

import java.util.function.Function;

public class MethodGoldenRatio extends OneDimensionalSearch {

    private static final double phi = (3 - Math.sqrt(5)) / 2;

    public MethodGoldenRatio(Function<Double, Double> function, double leftBorder, double rightBorder, double EPS) {
        super(function, leftBorder, rightBorder, EPS);
    }

    @Override
    public double getMinimalValue() {
        int iter = 1;
        double left = leftBorder;
        double right = rightBorder;
        while (!isEnd(left, right)) {
            double leftMid = runForLeftBorder(left, right);
            double rightMid = runForRightBorder(left, right);
            if (function.apply(leftMid) <= function.apply(rightMid)) {
                right = rightMid;
            } else {
                left = leftMid;
            }
            iter++;
        }
        return (left + right) / 2;
    }

    private boolean isEnd(double left, double right) {
        return right - left <= 2 * EPS;
    }

    private double runForLeftBorder(double left, double right) {
        return left + phi * (right - left);
    }

    private double runForRightBorder(double left, double right) {
        return right - phi * (right - left);
    }
}
