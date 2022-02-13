package lab2.method;

import lab2.function.Function;

import java.util.Arrays;

import static utils.MatrixUtil.*;

public class ConjugateGradient extends AbstractGradientMethod {

    public ConjugateGradient(double EPS) {
        super(EPS);
        this.iter = 1;
    }

    @Override
    public double[] findMin(Function function, double[] x0) {
        this.iter = 1;
        int dimensions = x0.length;
        double[] prevX = x0, nextX;
        double[] prevGrad = function.applyGrad(prevX), nextGrad;
        double[] prevP = multiply(prevGrad, -1), nextP;
        double prevGradNorm = norm(prevGrad), nextGradNorm;
        double alfa;
        double beta;
        for (int i = 0; i < dimensions && prevGradNorm >= EPS; i++) {
            double[] ap = function.getAX(prevP);
            alfa = prevGradNorm * prevGradNorm / scalarProduct(ap, prevP);
//            print(prevX, prevGrad);
            nextX = add(prevX, multiply(prevP, alfa));
            nextGrad = add(prevGrad, multiply(ap, alfa));
            nextGradNorm = norm(nextGrad);
            beta = nextGradNorm * nextGradNorm / (prevGradNorm * prevGradNorm);
            nextP = add(multiply(nextGrad, -1), multiply(prevP, beta));
            prevP = nextP;
            prevGrad = nextGrad;
            prevX = nextX;
            prevGradNorm = nextGradNorm;
            this.iter++;
        }
//        System.out.printf(
//                "–Conjugate gradient:\nIter : %d\nX : %s\nGrad : %s\n",
//                iter,
//                Arrays.toString(prevX),
//                Arrays.toString(prevGrad)
//        );
        return prevX;
    }

    @Override
    protected String getName() {
        return "Conjugate gradient";
    }

}