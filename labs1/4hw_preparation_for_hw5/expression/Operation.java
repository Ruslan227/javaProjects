package expression;

import expression.MiniExceptions.*;

public abstract class Operation implements CommonExpression {
    @Override
    public int evaluate(int x) throws CountException {
        return evaluate(x, 0, 0);
    }

    public abstract String getOperation();
}
