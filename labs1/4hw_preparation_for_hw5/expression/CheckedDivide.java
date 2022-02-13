package expression;

import expression.MiniExceptions.*;

public class CheckedDivide extends BinaryOperation {
    public CheckedDivide(CommonExpression arg1, CommonExpression arg2) {
        super(arg1, arg2);
    }

    @Override
    public int count(int a, int b) throws CountException {
        check(a, b);
        return a / b;
    }

    @Override
    public String getOperation() {
        return "/";
    }

    @Override
    public void check(int a, int b) throws CountException {
        if (b == 0) {
            throw new OperationException("Division by zero!");
        }

        if (a == Integer.MIN_VALUE && b == -1) {
            throw new OverflowException();
        }
    }
}
