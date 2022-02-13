package expression;

import expression.MiniExceptions.*;

public class CheckedAdd extends BinaryOperation {
    public CheckedAdd(CommonExpression arg1, CommonExpression arg2) {
        super(arg1, arg2);
    }

    @Override
    public int count(int a, int b) throws OverflowException {
        check(a, b);
        return a + b;
    }

    @Override
    public String getOperation() {
        return "+";
    }

    @Override
    public void check(int a, int b) throws OverflowException {
        if ((a > 0 && Integer.MAX_VALUE - a < b) || (a < 0 && Integer.MIN_VALUE - a > b)) {
            throw new OverflowException();
        }
    }
}
