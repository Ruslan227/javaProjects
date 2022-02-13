package parser.expressionClasses.binary;

import parser.expressionClasses.LogicalExpression;

public class Mul extends BinaryOperation {
    public Mul(LogicalExpression arg1, LogicalExpression arg2) {
        super(arg1, arg2, "*", true);
    }
}
