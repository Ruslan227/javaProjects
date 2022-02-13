package expression.exceptions;

public class ParsingException extends Exception {
    public ParsingException(final String message) {
        super(message);
    }

    static protected String getPlace(final int ind, final int size) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < ind; i++) {
            res.append('~');
        }
        res.append("^\n");
        return res.toString() + Colors.RESET;
    }
}