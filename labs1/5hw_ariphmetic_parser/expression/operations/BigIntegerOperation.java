package expression.operations;

import expression.exceptions.*;

import java.math.BigInteger;

public class BigIntegerOperation implements Operation<BigInteger> {
    public BigInteger parseNumber(final String number) throws BadConstantException {
        try {
            return new BigInteger(number);
        } catch (NumberFormatException NFE) {
            throw new BadConstantException();
        }
    }

    public BigInteger add(final BigInteger x, final BigInteger y) {
        return x.add(y);
    }

    public BigInteger sub(final BigInteger x, final BigInteger y) {
        return x.subtract(y);
    }

    public BigInteger mul(final BigInteger x, final BigInteger y) {
        return x.multiply(y);
    }

    private void checkZero(final BigInteger x, final String reason) throws BadOperationException {
        if (x.equals(BigInteger.ZERO)) {
            throw new BadOperationException(reason);
        }
    }

    public BigInteger div(final BigInteger x, final BigInteger y) throws BadOperationException {
        checkZero(y, "Division by zero");
        return x.divide(y);
    }

    public BigInteger uminus(final BigInteger x) {
        return x.negate();
    }

    public BigInteger count(final BigInteger x) {
        return BigInteger.valueOf(x.bitCount());
    }

    public BigInteger min(final BigInteger x, final BigInteger y) {
        return x.compareTo(y) == -1 ? x : y;
    }

    public BigInteger max(final BigInteger x, final BigInteger y) {
        return x.compareTo(y) == 1 ? x : y;
    }

}