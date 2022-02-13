package parser.tokens;

public enum Token {
    START,
    END,

    OP_FOLLOW,
    OP_OR,
    OP_AND,

    OPEN_BRACKET,
    CLOSE_BRACKET,

    OP_NEG,
    EXIST,
    UNIVERSAL,
    DOT,

    SPECIFIC_VAR,
    PREDICATE,
    EQ,

    ADD,
    MUL,

    APOSTROPHE,
    ZERO,

    ANY_EXPR,
    ANY_TERM,
    ANY_VAR,

    UNKNOWN
}
