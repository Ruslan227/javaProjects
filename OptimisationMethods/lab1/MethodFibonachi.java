package lab1;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class MethodFibonachi extends OneDimensionalSearch {

    /** Поле список чисел Фибоначчи */
    private final List<Long> fibonacci;
    /** Поле количество требуемых итераций */
    private int size = 1;
    /** Поле количество совершённых итераций */
    private int passed = 0;
    /** Поле длина начального отрезка */
    private final double lengthOfSection;

    public MethodFibonachi(Function<Double, Double> function, double leftBorder, double rightBorder, double EPS) {
        super(function, leftBorder, rightBorder, EPS);
        this.lengthOfSection = rightBorder - leftBorder;

        fibonacci = new ArrayList<>();
        fibonacci.add(0L);
        fibonacci.add(1L);
        while (lengthOfSection / EPS >= fibonacci.get(size)) {
            fibonacci.add(fibonacci.get(size) + fibonacci.get(size - 1));
            size++;
        }
    }

    @Override
    public double getMinimalValue() {
        int iter = 1;
        double left = leftBorder;
        double right = rightBorder;
        while (!isEnd()) {
            double leftMid = runForLeftBorder(left);
            double rightMid = runForRightBorder(left);
            if (function.apply(leftMid) <= function.apply(rightMid)) {
                right = rightMid;
            } else {
                left = leftMid;
            }
            iter++;
        }
        return (left + right) / 2;
    }

    private boolean isEnd() {
        return passed++ > size - 2;
    }

    private double runForLeftBorder(double left) {
        return left + lengthOfSection * fibonacci.get(size - passed - 1) / fibonacci.get(size);
    }

    private double runForRightBorder(double left) {
        return left + lengthOfSection * fibonacci.get(size - passed) / fibonacci.get(size);
    }
}



