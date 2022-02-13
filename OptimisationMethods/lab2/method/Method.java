package lab2.method;

import lab2.function.Function;

public interface Method {
    double[] findMin(Function function, double[] x0);

    int getIter();
}
