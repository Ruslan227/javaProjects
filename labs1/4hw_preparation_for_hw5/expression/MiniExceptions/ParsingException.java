package expression.MiniExceptions;

public class ParsingException extends Exception {
    public ParsingException(final String message) {
        super(message);
    }

    static protected StringBuilder getPlace(final int ind) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < ind; i++) {
            res.append(' ');
        }
        res.append("^\n");
        return res;
    }
}