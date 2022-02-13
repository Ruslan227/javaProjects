package parser.expressionClasses.binary;


import parser.expressionClasses.LogicalExpression;

public class Follow extends BinaryOperation  {

    public Follow(LogicalExpression arg1, LogicalExpression arg2) {
        super(arg1, arg2, "->", false);
    }

}
