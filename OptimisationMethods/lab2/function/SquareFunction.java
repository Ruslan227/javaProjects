package lab2.function;

import utils.MatrixUtil;

public class SquareFunction implements Function {

    private final double[][] A;
    private final double[] B;
    private final double C;

    public SquareFunction(double[][] a, double[] b, double c) {
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
        return MatrixUtil.multiply(A, x);
    }

}
