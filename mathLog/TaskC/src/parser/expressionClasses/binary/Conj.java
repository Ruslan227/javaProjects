package parser.expressionClasses.binary;

import parser.expressionClasses.LogicalExpression;

public class Conj extends BinaryOperation {

    public Conj(LogicalExpression arg1, LogicalExpression arg2) {
        super(arg1, arg2, "&", false);
    }

}
