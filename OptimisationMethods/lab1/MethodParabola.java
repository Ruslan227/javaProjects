package lab1;

import java.util.function.Function;

public class MethodParabola extends OneDimensionalSearch {

    public MethodParabola(Function<Double, Double> function, double leftBorder, double rightBorder, double EPS) {
        super(function, leftBorder, rightBorder, EPS);
    }

    @Override
    public double getMinimalValue() {
        int iter = 1;
        double middleStart;
        if (function.apply(leftBorder) < function.apply(rightBorder)) {
            if (function.apply(leftBorder) > function.apply(leftBorder + EPS)) {
                middleStart = leftBorder + EPS;
            } else {
                middleStart = leftBorder;
            }
        } else {
            if (function.apply(rightBorder) > function.apply(rightBorder - EPS)) {
                middleStart = rightBorder - EPS;
            } else {
                middleStart = rightBorder;
            }
        }
        if (leftBorder == middleStart || middleStart == rightBorder) {
            return middleStart;
        }
        double x1 = leftBorder;
        double x2 = middleStart;
        double x3 = rightBorder;
        double prevX, nextX, fNextX, f1, f2, f3, a1, a2;
        nextX = x1;
        f1 = function.apply(x1);
        f2 = function.apply(x2);
        f3 = function.apply(x3);
        do {
            prevX = nextX;
            a1 = (f2 - f1) / (x2 - x1);
            a2 = ((f3 - f1) / (x3 - x1) - a1) / (x3 - x2);
            nextX = (x1 + x2 - a1 / a2) / 2;
            fNextX = function.apply(nextX);
            if (nextX < x2) {
                if (fNextX >= f2) {
                    x1 = nextX;
                    f1 = fNextX;
                } else {
                    x3 = x2;
                    f3 = f2;
                    x2 = nextX;
                    f2 = fNextX;
                }
            } else {
                if (fNextX <= f2) {
                    x1 = x2;
                    f1 = f2;
                    x2 = nextX;
                    f2 = fNextX;
                } else {
                    x3 = nextX;
                    f3 = fNextX;
                }
            }
            iter++;
        } while (Math.abs(prevX - nextX) > EPS);
        return nextX;
    }
}





