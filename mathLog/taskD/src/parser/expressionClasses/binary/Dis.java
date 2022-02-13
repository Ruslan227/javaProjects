package parser.expressionClasses.binary;

import parser.expressionClasses.LogicalExpression;

public class Dis extends BinaryOperation {

    public Dis(LogicalExpression arg1, LogicalExpression arg2) {
        super(arg1, arg2, "|", false);
    }

}
