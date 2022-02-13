package expression.expressions;

import expression.operations.*;

public class Count<T> extends AbstractUnaryOperator<T> {
    public Count(final TripleExpression<T> x, final Operation<T> y) {
        super(x, y);
    }

    protected T apply(final T x) {
        return op.count(x);
    }
}