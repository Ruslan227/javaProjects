package expression;

import expression.MiniExceptions.*;

public class CheckedPow2 extends UnaryOperation {
    public CheckedPow2(CommonExpression arg) {
        super(arg);
    }

    @Override
    public int count(int a) throws CountException {
        check(a);
        return 1 << a;
    }

    @Override
    public String getOperation() {
        return "pow2 ";
    }

    @Override
    public void check(int a) throws OverflowException {
        if (a > 31 || a < 0) {
            throw new OverflowException();
        }
    }
}
