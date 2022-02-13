package expression.operations;

public class LongOperation implements Operation<Long> {
    private final boolean checked;

    public LongOperation(final boolean check) {
        checked = check;
    }

    public Long parseNumber(final String number) {
            return Long.parseLong(number);
    }


    public Long add(final Long x, final Long y)  {

        return x + y;
    }


    public Long sub(final Long x, final Long y)  {

        return x - y;
    }



    public Long mul(final Long x, final Long y) {
        return x * y;
    }



    public Long div(final Long x, final Long y) {

        return x / y;
    }


    public Long uminus(final Long x) {

        return -x;
    }

    public Long count(final Long x) {
        return (long) Long.bitCount(x);
    }

    public Long min(final Long x, final Long y) {
        return x < y ? x : y;
    }

    public Long max(final Long x, final Long y) {
        return x > y ? x : y;
    }
}