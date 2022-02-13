package parser.tokens;

public enum Tokens {
    OPEN_BRACKET,
    CLOSE_BRACKET,
    OP_AND,
    OP_FOLLOW,
    OP_OR,
    OP_NEG,
    TURNSTILE,
    VAR,
    START,
    END;

    public boolean isBinaryOperation() {
        return this == OP_AND || this == OP_FOLLOW || this == OP_OR;
    }


}
