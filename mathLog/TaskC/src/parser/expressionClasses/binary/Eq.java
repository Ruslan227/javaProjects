package parser.expressionClasses.binary;

import parser.expressionClasses.LogicalExpression;

public class Eq extends BinaryOperation {
    public Eq(LogicalExpression arg1, LogicalExpression arg2) {
        super(arg1, arg2, "=", false);
    }
}
