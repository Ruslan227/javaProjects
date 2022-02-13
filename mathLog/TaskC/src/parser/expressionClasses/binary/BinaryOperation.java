package parser.expressionClasses.binary;

import parser.expressionClasses.AbstractLogicalExpression;
import parser.expressionClasses.LogicalExpression;
import parser.expressionClasses.any.AbstractAny;
import parser.expressionClasses.any.AnyTerm;
import parser.expressionClasses.variables.SpecificVar;

import java.util.*;

public abstract class BinaryOperation extends AbstractLogicalExpression {
    private final LogicalExpression arg1, arg2;
    private final String operationSymbol;
    private final boolean isTermOperation;

    public BinaryOperation(LogicalExpression arg1, LogicalExpression arg2,
                           String operationSymbol, boolean isTermOperation) {
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.operationSymbol = operationSymbol;
        this.isTermOperation = isTermOperation;
    }

    public LogicalExpression getArg1() {
        return arg1;
    }

    public LogicalExpression getArg2() {
        return arg2;
    }

    @Override
    public void getAllVarsImpl(Set<SpecificVar> specificVars) {
        arg1.getAllVarsImpl(specificVars);
        arg2.getAllVarsImpl(specificVars);
    }

    @Override
    public void getBoundedVarsImpl(Set<SpecificVar> boundedVars, Set<SpecificVar> freeVars) {
        Set<SpecificVar> boundedCopy = new HashSet<>(boundedVars);
        arg1.getBoundedVarsImpl(boundedCopy, freeVars);
        arg2.getBoundedVarsImpl(boundedVars, freeVars);
        boundedVars.addAll(boundedCopy);
        boundedVars.removeAll(freeVars);
    }

    @Override
    protected boolean matchWithImplNext(LogicalExpression expression, Map<String, AbstractAny> substitutions) {
        BinaryOperation binaryOp = (BinaryOperation) expression;
        return arg1.matchWithImpl(binaryOp.getArg1(), substitutions) &&
                arg2.matchWithImpl(binaryOp.getArg2(), substitutions);
    }

    @Override
    protected boolean extractTermAsImplNext(LogicalExpression expression, SpecificVar specificVar, AnyTerm substitution) {
        BinaryOperation binaryOp = (BinaryOperation) expression;
        return arg1.extractTermAsImpl(binaryOp.getArg1(), specificVar, substitution) &&
                arg2.extractTermAsImpl(binaryOp.getArg2(), specificVar, substitution);
    }

    @Override
    public boolean isTerm() {
        return isTermOperation && arg1.isTerm() && arg2.isTerm();
    }

    @Override
    public String toString() {
        return "(" + arg1.toString() + operationSymbol + arg2.toString() + ")";
    }

    @Override
    public boolean safeEquals(Object o) {
        BinaryOperation that = (BinaryOperation) o;
        return arg1.equals(that.getArg1()) && arg2.equals(that.getArg2());
    }

    @Override
    public int hashCode() {
        return Objects.hash(arg1, arg2, operationSymbol);
    }
}
