package expression.expressions;

import expression.exceptions.*;

public interface TripleExpression<T> {
    T evaluate(T x, T y, T z) throws CalculatingException;
}
