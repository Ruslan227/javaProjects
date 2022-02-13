package expression.expressions;

import expression.exceptions.*;
import expression.operations.*;

public class Divide<T> extends AbstractBinaryOperator<T> {
    public Divide(final TripleExpression<T> x, final TripleExpression<T> y, final Operation<T> z) {
        super(x, y, z);
    }

    protected T apply(final T x, final T y) throws CalculatingException {
        return op.div(x, y);
    }
}