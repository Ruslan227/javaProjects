package parser.expressionClasses.unary;

import parser.expressionClasses.LogicalExpression;

public class Neg extends UnaryOperation {
    public Neg(LogicalExpression arg) {
        super(arg, "!", false);
    }
}
