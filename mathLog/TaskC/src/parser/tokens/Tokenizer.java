package parser.tokens;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class Tokenizer {
    private static final Map<Token, Function<CurState, String>> NEXTS;

    static {
        NEXTS = new HashMap<>();

        NEXTS.put(Token.OP_FOLLOW, curState -> isNext("->", curState));
        NEXTS.put(Token.OP_OR, curState -> isNext("|", curState));
        NEXTS.put(Token.OP_AND, curState -> isNext("&", curState));

        NEXTS.put(Token.OPEN_BRACKET, curState -> isNext("(", curState));
        NEXTS.put(Token.CLOSE_BRACKET, curState -> isNext(")", curState));

        NEXTS.put(Token.OP_NEG, curState -> isNext("!", curState));
        NEXTS.put(Token.EXIST, curState -> isNext("?", curState));
        NEXTS.put(Token.UNIVERSAL, curState -> isNext("@", curState));
        NEXTS.put(Token.DOT, curState -> isNext(".", curState));

        NEXTS.put(Token.SPECIFIC_VAR, curState -> {
            char cur = curState.expression.charAt(curState.ind);
            return cur >= 'a' && cur <= 'z' ? Character.toString(cur) : null;
        });
        NEXTS.put(Token.PREDICATE, curState -> {
            char cur = curState.expression.charAt(curState.ind);
            return cur >= 'A' && cur <= 'Z' ? Character.toString(cur) : null;
        });

        NEXTS.put(Token.EQ, curState -> isNext("=", curState));

        NEXTS.put(Token.ADD, curState -> isNext("+", curState));
        NEXTS.put(Token.MUL, curState -> isNext("*", curState));

        NEXTS.put(Token.APOSTROPHE, curState -> isNext("'", curState));
        NEXTS.put(Token.ZERO, curState -> isNext("0", curState));

        NEXTS.put(Token.ANY_EXPR, curState -> startsWith('#', curState));
        NEXTS.put(Token.ANY_TERM, curState -> startsWith('$', curState));
        NEXTS.put(Token.ANY_VAR, curState -> startsWith('~', curState));
    }

    private static String startsWith(char prefix, CurState curState) {
        if (curState.expression.charAt(curState.ind) != prefix) return null;
        curState.ind++;
        return prefix + getNextWord(curState);
    }

    private static String getNextWord(CurState curState) {
        String expression = curState.expression;
        int ind = curState.ind;
        while (ind < expression.length()) {
            char ch = expression.charAt(ind);
            if (!(Character.isLetter(ch) || ch == '-')) {
                break;
            }
            ind++;
        }
        return expression.substring(curState.ind, ind);
    }

    private final String expression;
    private final Set<Token> unsupportedTokens;
    private Token token = Token.START;
    private String tokenValue = null;
    private int ind = 0;
    public Tokenizer(Tokenizer tokenizer) {
        expression = tokenizer.expression;
        token = tokenizer.token;
        tokenValue = tokenizer.tokenValue;
        ind = tokenizer.ind;
        unsupportedTokens = tokenizer.unsupportedTokens;
    }

    public Tokenizer(String expression, Set<Token> unsupportedTokens) {
        this.expression = expression;
        this.unsupportedTokens = unsupportedTokens;
    }

    private static String isNext(String value, CurState curState) {
        String expression = curState.expression;
        int ind = curState.ind;
        for (int i = 0; i < value.length(); i++) {
            if (ind >= expression.length() || value.charAt(i) != expression.charAt(ind)) {
                return null;
            }
            ind++;
        }
        return expression.substring(curState.ind, ind);
    }

    private void skipWhiteSpace() {
        while (ind < expression.length() && Character.isWhitespace(expression.charAt(ind))) {
            ind++;
        }
        if (ind == expression.length()) {
            token = Token.END;
            tokenValue = null;
        }
    }

    public void nextToken() {
        skipWhiteSpace();
        if (token == Token.END) {
            return;
        }
        CurState curState = new CurState(expression, ind);
        token = Token.UNKNOWN;
        tokenValue = null;
        for (var next : NEXTS.entrySet()) {
            if (unsupportedTokens.contains(next.getKey())) {
                continue;
            }
            String nextValue = next.getValue().apply(curState);
            if (nextValue != null) {
                token = next.getKey();
                tokenValue = nextValue;
                ind += nextValue.length();
                break;
            }
        }
    }

    public Token getToken() {
        return token;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    private static class CurState {
        final String expression;
        int ind;

        public CurState(final String expression, int ind) {
            this.expression = expression;
            this.ind = ind;
        }
    }
}
