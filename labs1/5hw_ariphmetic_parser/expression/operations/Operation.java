package expression.operations;

import expression.exceptions.*;

public interface Operation<T> {
    T parseNumber(final String number) throws BadConstantException;

    T add(final T x, final T y) throws ValueOverflowException;

    T sub(final T x, final T y) throws ValueOverflowException;

    T mul(final T x, final T y) throws ValueOverflowException;

    T div(final T x, final T y) throws CalculatingException;

    T uminus(final T x) throws ValueOverflowException;

    T count(final T x);

    T min(final T x, final T y);

    T max(final T x, final T y);
}