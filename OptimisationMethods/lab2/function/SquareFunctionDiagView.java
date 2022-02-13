package lab2.function;

import utils.MatrixUtil;

public class SquareFunctionDiagView implements Function {
    private final double[] A;
    private final double[] B;
    private final double C;

    public SquareFunctionDiagView(double[] a, double[] b, double c) {
        A = a;
        B = b;
        C = c;
    }

    @Override
    public double apply(double[] x) {
        return 0.5 * MatrixUtil.scalarProduct(getAX(x), x) + MatrixUtil.scalarProduct(B, x) + C;
    }

    @Override
    public double[] applyGrad(double[] x) {
        return MatrixUtil.add(getAX(x), B);
    }

    @Override
    public double[] getAX(double[] x) {
        return MatrixUtil.dotProduct(A, x);
    }
}
