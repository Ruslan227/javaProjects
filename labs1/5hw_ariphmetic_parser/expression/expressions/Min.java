package expression.expressions;

import expression.operations.*;

public class Min<T> extends AbstractBinaryOperator<T> {
    public Min(final TripleExpression<T> x, final TripleExpression<T> y, final Operation<T> z) {
        super(x, y, z);
    }

    protected T apply(final T x, final T y) {
        return op.min(x, y);
    }
}