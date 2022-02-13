package expression;

import java.util.Objects;
import expression.MiniExceptions.*;

public abstract class UnaryOperation extends Operation {
    private final CommonExpression arg1;

    protected UnaryOperation(CommonExpression arg1) {
        this.arg1 = arg1;
    }

    @Override
    public String toString() {
        return getOperation() + arg1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && getClass() == obj.getClass()) {
            UnaryOperation second = (UnaryOperation) obj;
            return Objects.equals(arg1, second.arg1);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(arg1) * 31 + Objects.hashCode(getClass());
    }

    @Override
    public int evaluate(int x, int y, int z) throws CountException {
        return count(arg1.evaluate(x, y, z));
    }

    public abstract void check(int a) throws CountException;

    public abstract int count(int a) throws CountException;
}
