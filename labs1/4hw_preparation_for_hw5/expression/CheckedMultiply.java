package expression;

import expression.MiniExceptions.*;

public class CheckedMultiply extends BinaryOperation {
    public CheckedMultiply(CommonExpression arg1, CommonExpression arg2) {
        super(arg1, arg2);
    }

    @Override
    public int count(int a, int b) throws CountException {
        check(a, b);
        return a * b;
    }

    @Override
    public String getOperation() {
        return "*";
    }

    @Override
    public void check(int a, int b) throws CountException {
        if ((a > 0 && b > 0 && Integer.MAX_VALUE / a < b) || (a < 0 && b < 0 && Integer.MAX_VALUE / a > b)) {
            throw new OverflowException();
        }

        if ((a > 0 && b < 0 && Integer.MIN_VALUE / a > b) || (a < 0 && b > 0 && Integer.MIN_VALUE / b > a)) {
            throw new OverflowException();
        }
    }
}
