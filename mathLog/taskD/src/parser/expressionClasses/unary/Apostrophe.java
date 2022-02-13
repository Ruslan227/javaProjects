package parser.expressionClasses.unary;

import parser.expressionClasses.LogicalExpression;

public class Apostrophe extends UnaryOperation {
    public Apostrophe(LogicalExpression arg) {
        super(arg, "'", true);
    }

    @Override
    public String toString() {
        return getArg().toString() + "'";
    }
}
