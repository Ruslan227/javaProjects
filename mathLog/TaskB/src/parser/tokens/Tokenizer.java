package parser.tokens;

public class Tokenizer {
    private final String expression;
    private Tokens token = Tokens.START;
    private int ind = -1;
    private String lastVar;

    public Tokenizer(String expression) {
        this.expression = expression;
    }

    public void skipWhiteSpace() {
        while (Character.isWhitespace(expression.charAt(ind))) {
            ind++;
            if (ind >= expression.length()) {
                token = Tokens.END;
                break;
            }
        }

    }

    public void nextToken() {
        ind++;
        if (ind < expression.length())
            skipWhiteSpace();
        else
            token = Tokens.END;

        if (token == Tokens.END)
            return;

        switch (expression.charAt(ind)) {
            case '(':
                token = Tokens.OPEN_BRACKET;
                break;
            case ')':
                token = Tokens.CLOSE_BRACKET;
                break;
            case '|':
                token = Tokens.OP_OR;
                break;
            case '&':
                token = Tokens.OP_AND;
                break;
            case '!':
                token = Tokens.OP_NEG;
                break;
            case '→':
                token = Tokens.OP_FOLLOW;
                break;
            case '⊢':
                token = Tokens.TURNSTILE;
                break;
            default:
                token = Tokens.VAR;
                parseVar();
                break;
        }
    }

    public void parseVar() {
        var res = new StringBuilder();
        char symb = expression.charAt(ind);

        while ((symb >= 'A' && symb <= 'Z') || (symb >= '0' && symb <= '9') || (symb == '\'')) {
            res.append(symb);
            ind++;
            if (ind >= expression.length()) {
                break;
            }
            symb = expression.charAt(ind);
        }
        ind--;
        lastVar = res.toString();
    }

    public Tokens getToken() {
        return token;
    }

    public String getLastVar() {
        return lastVar;
    }
}
