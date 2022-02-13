package expression.operations;

import expression.exceptions.*;

public class DoubleOperation implements Operation<Double> {
    public Double parseNumber(final String number) throws BadConstantException {
        try {
            return Double.parseDouble(number);
        } catch (NumberFormatException NFM) {
            throw new BadConstantException();
        }
    }

    public Double add(final Double x, final Double y) {
        return x + y;
    }

    public Double sub(final Double x, final Double y) {
        return x - y;
    }

    public Double mul(final Double x, final Double y) {
        return x * y;
    }

    public Double div(final Double x, final Double y) {
        return x / y;
    }

    public Double uminus(final Double x) {
        return -x;
    }

    public Double count(final Double x) {
        long tmp = Long.toBinaryString(Double.doubleToRawLongBits(x)).chars().filter(i -> i == '1').count();
        return Double.valueOf(Long.valueOf(tmp).doubleValue());
    }

    public Double min(final Double x, final Double y) {
        return x < y ? x : y;
    }

    public Double max(final Double x, final Double y) {
        return x > y ? x : y;
    }
}