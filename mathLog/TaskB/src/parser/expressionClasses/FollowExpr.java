package parser.expressionClasses;


public class FollowExpr extends BinaryOperation implements LogicalObject {

    public FollowExpr(LogicalObject arg1, LogicalObject arg2) {
        super(arg1, arg2, "->");
    }

}
