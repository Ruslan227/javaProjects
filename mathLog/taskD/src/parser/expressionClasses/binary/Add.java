package parser.expressionClasses.binary;

import parser.expressionClasses.LogicalExpression;

public class Add extends BinaryOperation {
    public Add(LogicalExpression arg1, LogicalExpression arg2) {
        super(arg1, arg2, "+", true);
    }
}
