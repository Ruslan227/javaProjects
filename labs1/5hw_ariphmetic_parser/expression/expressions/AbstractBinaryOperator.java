package expression.expressions;

import expression.exceptions.*;
import expression.operations.*;

public abstract class AbstractBinaryOperator<T> implements TripleExpression<T> {
    private final TripleExpression<T> firstOperand, secondOperand;
    protected final Operation<T> op;

    protected AbstractBinaryOperator(final TripleExpression<T> x, final TripleExpression<T> y, final Operation<T> z) {
        firstOperand = x;
        secondOperand = y;
        op = z;
    }

    protected abstract T apply(final T x, final T y) throws CalculatingException;

    public T evaluate(final T x, final T y, final T z) throws CalculatingException {
        return apply(firstOperand.evaluate(x, y, z), secondOperand.evaluate(x, y, z));
    }

}