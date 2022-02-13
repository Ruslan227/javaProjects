package lab1;

import java.util.function.Function;

public class MethodCombinationOfBrent extends OneDimensionalSearch {

    private final static double phi = (3 - Math.sqrt(5)) / 2;

    public MethodCombinationOfBrent(Function<Double, Double> function, double leftBorder, double rightBorder, double EPS) {
        super(function, leftBorder, rightBorder, EPS);
    }

    @Override
    public double getMinimalValue() {
        int iter = 1;
        //начальные значения
        double a = leftBorder;      //левая граница
        double c = rightBorder;     //правая граница
        double x = runForLeftBorder(leftBorder, rightBorder); //условный минимум
        double w = x;               //2-ой по минимальности
        double v = x;               //предыдущее значение w
        double fx = function.apply(x);//значение функции в х
        double fw = fx;             //значение функции в w
        double fv = fx;             //значение функции в v
        //длины шагов
        double d = (c - a);
        double e = d;
        double g;
        //локальный eps
        double tol;
        double u = 0;
        while (true) {
            //lastLen = c - a;
            g = e;
            e = d;
            boolean uIsGood = false;
            tol = EPS * Math.abs(x) + EPS / 10.0;
            //проверка на завершение
            if (Math.abs(x - (a + c) / 2) + (c - a) / 2 <= 2 * tol) {
                break;
            }
            //пытаемся использовать Метод Парабол
            if (x != w && x != v && w != v && fx != fv && fx != fw && fv != fw) {
                //find u (min of par on v,x,w)
                u = findMinParabolaNoOrder(v, x, w, fv, fx, fw);
                //проверяем, что u попадает в диапазон и достаточно хорошо сокращает отрезок
                if (a <= u && u <= c && Math.abs(u - x) < g / 2) {
                    //говорим что u нам подходит
                    uIsGood = true;
                    //если u оказался слишком близко к границе, то мы двигаем его в x
                    if (u - a < 2 * tol || c - u < 2 * tol) {
                        u = x - Math.signum(x - (a + c) / 2) * tol;
                    }
                }
            }
            //когда метод парабол не прошел, переходим на Золотое сечение
            if (!uIsGood) {
                if (x < (a + c) / 2) {
                    u = runForLeftBorder(x, c);
                    e = c - x;
                } else {
                    u = runForRightBorder(a, x);
                    e = x - a;
                }
            }
            //разводим u и x на tol(лок eps)
            if (Math.abs(u - x) < tol) {
                u = x + Math.signum(u - x) * tol;
            }
            d = Math.abs(u - x);
            double fu = function.apply(u);
            //переставляем границы
            if (fu <= fx) {
                if (u >= x) {
                    a = x;
                } else {
                    c = x;
                }
                v = w;
                w = x;
                x = u;
                fv = fw;
                fw = fx;
                fx = fu;
            } else {
                if (u >= x) {
                    c = u;
                } else {
                    a = u;
                }
                if (fu <= fw || w == x) {
                    v = w;
                    w = u;
                    fv = fw;
                    fw = fu;
                } else if (fu <= fv || v == x || v == w) {
                    v = u;
                    fv = fu;
                }
            }
            iter++;
        }
        return x;
    }

    private double findMinParabolaNoOrder(double x1, double x2, double x3, double f1, double f2, double f3) {
        if (x1 < x2 && x2 < x3) {
            return findMinOfParabola(x1, x2, x3, f1, f2, f3);
        }
        if (x3 < x2 && x2 < x1) {
            return findMinOfParabola(x3, x2, x1, f3, f2, f1);
        }
        if (x2 < x1 && x1 < x3) {
            return findMinOfParabola(x2, x1, x3, f2, f1, f3);
        }
        if (x3 < x1 && x1 < x2) {
            return findMinOfParabola(x3, x1, x2, f3, f1, f2);
        }
        if (x1 < x3 && x3 < x2) {
            return findMinOfParabola(x1, x3, x2, f1, f3, f2);
        }
        if (x2 < x3 && x3 < x1) {
            return findMinOfParabola(x2, x3, x1, f2, f3, f1);
        }
        return -1;
    }

    private double findMinOfParabola(double x1, double x2, double x3, double f1, double f2, double f3) {
        double a1 = (f2 - f1) / (x2 - x1);
        double a2 = ((f3 - f1) / (x3 - x1) - a1) / (x3 - x2);
        return (x1 + x2 - a1 / a2) / 2;
    }

    private double runForLeftBorder(double left, double right) {
        return left + phi * (right - left);
    }

    private double runForRightBorder(double left, double right) {
        return right - phi * (right - left);
    }

}
