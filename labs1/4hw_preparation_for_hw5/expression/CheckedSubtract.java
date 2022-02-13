package expression;

import expression.MiniExceptions.*;

public class CheckedSubtract extends BinaryOperation {
    public CheckedSubtract(CommonExpression arg1, CommonExpression arg2) {
        super(arg1, arg2);
    }

    @Override
    public int count(int a, int b) throws CountException {
        check(a, b);
        return a - b;
    }

    @Override
    public String getOperation() {
        return "-";
    }

    @Override
    public void check(int a, int b) throws OverflowException {
        if ((a >= 0 && b < 0 && a - Integer.MAX_VALUE > b) || (a <= 0 && b > 0 && Integer.MIN_VALUE - a > -b)) {
            throw new OverflowException();
        }
    }
}
