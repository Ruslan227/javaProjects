package parser.expressionClasses;

import java.util.Objects;

public class BinaryOperation implements Comparable<LogicalObject> {
    private final LogicalObject arg1, arg2;
    private final String operationSymbol;

    public BinaryOperation(LogicalObject arg1, LogicalObject arg2, String operationSymbol) {
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.operationSymbol = operationSymbol;
    }

    public LogicalObject getArg1() {
        return arg1;
    }

    public LogicalObject getArg2() {
        return arg2;
    }

    public String getOperationSymbol() {
        return operationSymbol;
    }

    @Override
    public String toString() {
        return "(" + arg1.toString() + operationSymbol + arg2.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinaryOperation that = (BinaryOperation) o;
        return getArg1().equals(that.getArg1()) &&
                getArg2().equals(that.getArg2()) &&
                getOperationSymbol().equals(that.getOperationSymbol());
    }

    @Override
    public int hashCode() {
        return Objects.hash(arg1, arg2, operationSymbol);
    }

    @Override
    public int compareTo(LogicalObject o) {
        return hashCode() - o.hashCode();
    }
}
