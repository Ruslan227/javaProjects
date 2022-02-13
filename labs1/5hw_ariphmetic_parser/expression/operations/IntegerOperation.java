package expression.operations;

import expression.exceptions.*;

public class IntegerOperation implements Operation<Integer> {
    private final boolean checked;

    public IntegerOperation(final boolean check) {
        checked = check;
    }

    public Integer parseNumber(final String number) throws BadConstantException {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new BadConstantException();
        }
    }

    private void checkAdd(final Integer x, final Integer y) throws ValueOverflowException {
        if (x > 0 && Integer.MAX_VALUE - x < y) {
            throw new ValueOverflowException();
        }
        if (x < 0 && Integer.MIN_VALUE - x > y) {
            throw new ValueOverflowException();
        }
    }

    public Integer add(final Integer x, final Integer y) throws ValueOverflowException {
        if (checked) {
            checkAdd(x, y);
        }
        return x + y;
    }

    private void checkSub(final Integer x, final Integer y) throws ValueOverflowException {
        if (x >= 0 && y < 0 && x - Integer.MAX_VALUE > y) {
            throw new ValueOverflowException();
        }
        if (x <= 0 && y > 0 && Integer.MIN_VALUE - x > -y) {
            throw new ValueOverflowException();
        }
    }

    public Integer sub(final Integer x, final Integer y) throws ValueOverflowException {
        if (checked) {
            checkSub(x, y);
        }
        return x - y;
    }

    private void checkMul(final Integer x, final Integer y) throws ValueOverflowException {
        if (x > 0 && y > 0 && Integer.MAX_VALUE / x < y) {
            throw new ValueOverflowException();
        }
        if (x > 0 && y < 0 && Integer.MIN_VALUE / x > y) {
            throw new ValueOverflowException();
        }
        if (x < 0 && y > 0 && Integer.MIN_VALUE / y > x) {
            throw new ValueOverflowException();
        }
        if (x < 0 && y < 0 && Integer.MAX_VALUE / x > y) {
            throw new ValueOverflowException();
        }
    }

    public Integer mul(final Integer x, final Integer y) throws ValueOverflowException {
        if (checked) {
            checkMul(x, y);
        }
        return x * y;
    }

    private void checkDiv(final Integer x, final Integer y) throws ValueOverflowException {
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new ValueOverflowException();
        }
    }

    private void checkZero(final int y, final String reason) throws BadOperationException {
        if (y == 0) {
            throw new BadOperationException(reason);
        }
    }

    public Integer div(final Integer x, final Integer y) throws BadOperationException, ValueOverflowException {
        checkZero(y, "Division by zero");
        if (checked) {
            checkDiv(x, y);
        }
        return x / y;
    }

    private void checkNot(final Integer x) throws ValueOverflowException {
        if (x == Integer.MIN_VALUE) {
            throw new ValueOverflowException();
        }
    }

    public Integer uminus(final Integer x) throws ValueOverflowException {
        if (checked) {
            checkNot(x);
        }
        return -x;
    }

    public Integer count(final Integer x) {
        return Integer.bitCount(x);
    }

    public Integer min(final Integer x, final Integer y) {
        return x < y ? x : y;
    }

    public Integer max(final Integer x, final Integer y) {
        return x > y ? x : y;
    }
}