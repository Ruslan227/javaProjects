package lab2.method;

import java.util.Arrays;

public abstract class AbstractGradientMethod implements Method {
    protected final double EPS;
    protected int iter;

    public AbstractGradientMethod(final double EPS) {
        this.EPS = EPS;
        this.iter = 1;
    }

    protected void print(final double[] x, final double[] grad) {
        System.out.println(String.format("%s : %s", Arrays.toString(x), Arrays.toString(grad)));
    }

    abstract protected String getName();

    public String toString() {
        return String.format("–%s:\n Iterations: %d", getName(), iter);
    }

    public int getIter() {
        return this.iter;
    }
}
