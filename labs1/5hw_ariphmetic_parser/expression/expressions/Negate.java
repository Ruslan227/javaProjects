package expression.expressions;

import expression.exceptions.*;
import expression.operations.*;

public class Negate<T> extends AbstractUnaryOperator<T> {
    public Negate(final TripleExpression<T> x, final Operation<T> y) {
        super(x, y);
    }

    protected T apply(final T x) throws ValueOverflowException {
        return op.uminus(x);
    }
}