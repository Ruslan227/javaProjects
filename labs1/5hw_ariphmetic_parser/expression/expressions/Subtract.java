package expression.expressions;

import expression.exceptions.*;
import expression.operations.*;

public class Subtract<T> extends AbstractBinaryOperator<T> {
    public Subtract(final TripleExpression<T> x, final TripleExpression<T> y, final Operation<T> z) {
        super(x, y, z);
    }

    protected T apply(final T x, final T y) throws ValueOverflowException {
        return op.sub(x, y);
    }
}