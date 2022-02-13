package expression.parser;

import expression.exceptions.*;
import expression.expressions.*;

public interface Parser<T> {
    TripleExpression<T> parse(String expression) throws ParsingException;
}