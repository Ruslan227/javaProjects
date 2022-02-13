package expression;

import expression.MiniExceptions.*;

public class CheckedNegate extends UnaryOperation {
    public CheckedNegate(CommonExpression arg) {
        super(arg);
    }

    @Override
    public int count(int a) throws CountException {
        check(a);
        return -a;
    }

    @Override
    public String getOperation() {
        return "-";
    }

    @Override
    public void check(int a) throws OverflowException {
        if (a == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }
}
