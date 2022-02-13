package lab1;

import java.util.Objects;
import java.util.function.Function;

/**
 * Ищет минимум переданной функции на заданном отрезке.
 */
public abstract class OneDimensionalSearch {

    protected final Function<Double, Double> function;
    protected final double leftBorder, rightBorder;
    protected final double EPS;

    /**
     * Создает минимализатор функции function на отрезке [leftBorder, rightBorder] с точностью вычислений EPS
     *
     * @param function                  Минимализируемая функция
     * @param leftBorder                Левая граница отрезка
     * @param rightBorder               Правая граница отрезка
     * @param EPS                       Точность решения
     * @throws NullPointerException     Если переданный объект функции является null
     * @throws IllegalArgumentException Если хотя бы одна из переданных границ является бесконечностью или NaN или если leftBorder > rightBorder
     */
    public OneDimensionalSearch(Function<Double, Double> function, double leftBorder, double rightBorder, double EPS) {
        Objects.requireNonNull(function, "Given function is null");
        if (Double.isNaN(leftBorder) || Double.isNaN(rightBorder) ||
                Double.isInfinite(leftBorder) || Double.isInfinite(rightBorder) ||
                leftBorder > rightBorder) {
            throw new IllegalArgumentException("leftBorder > rightBorder");
        }
        this.function = function;
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
        this.EPS = EPS;
    }

    /**
     * Возвращает значение минимализуемой функции в x
     *
     * @param x                         Точка, в которой необходимо найти значение
     * @return                          Значение function в x
     * @throws IllegalArgumentException Если x не принадлежит [leftBorder, rightBorder]
     */
    public double apply(double x) {
        checkBorders(x);
        return function.apply(x);
    }

    /**
     * Найти точку минимума.
     *
     * @return Точка минимума
     */
    abstract public double getMinimalValue();

    /**
     * Проверяет, что точка x не выходит за пределы поиска
     * @param x Проверяемая точка
     */
    private void checkBorders(double x) {
        if (x < leftBorder || rightBorder < x) {
            throw new IllegalArgumentException("Given number " + x + " is out of allowed interval: [" + leftBorder + "; " + rightBorder + "]");
        }
    }

    protected boolean isLess(double x, double y) {
        return x - y < EPS;
    }
    protected boolean isBigger(double x, double y) {
        return x - y > EPS;
    }
    protected boolean isLessOrEqual(double x, double y) {
        return x - y <= EPS;
    }
    protected boolean isBiggerOrEqual(double x, double y) {
        return x - y >= EPS;
    }
    protected boolean isEqual(double x, double y) {
        return Math.abs(x - y) <= EPS;
    }
}
