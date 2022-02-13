package expression;

import java.util.Objects;
import expression.MiniExceptions.*;

public abstract class BinaryOperation extends Operation {
    private final CommonExpression arg1;
    private final CommonExpression arg2;

    protected BinaryOperation(CommonExpression arg1, CommonExpression arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public String toString() {
        return "(" + arg1 + ' ' + getOperation() + ' ' + arg2 + ')';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && getClass() == obj.getClass()) {
            BinaryOperation second = (BinaryOperation) obj;
            return Objects.equals(arg1, second.arg1) && Objects.equals(arg2, second.arg2);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(arg1, arg2, getClass());
    }

    @Override
    public int evaluate(int x, int y, int z) throws CountException {
        return count(arg1.evaluate(x, y, z), arg2.evaluate(x, y, z));
    }

    public abstract void check(int a, int b) throws CountException;

    public abstract int count(int a, int b) throws CountException;
}
