package expression;

import expression.MiniExceptions.*;

public class CheckedLog2 extends UnaryOperation {
    public CheckedLog2(CommonExpression arg) {
        super(arg);
    }

    @Override
    public int count(int a) throws CountException {
        check(a);
        int result = 0;
        while (a != 0 && a != 1) {
            result++;
            a /= 2;
        }
        return result;
    }

    @Override
    public String getOperation() {
        return "log2 ";
    }

    @Override
    public void check(int a) throws CountException {
        if (a < 0) {
            throw new OperationException("Log2 from negative");
        }

        if (a == 0) {
            throw new OperationException("Log2 from zero");
        }
    }
}
