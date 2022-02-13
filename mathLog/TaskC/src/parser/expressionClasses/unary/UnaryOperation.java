package parser.expressionClasses.unary;

import parser.expressionClasses.AbstractLogicalExpression;
import parser.expressionClasses.LogicalExpression;
import parser.expressionClasses.any.AbstractAny;
import parser.expressionClasses.any.AnyTerm;
import parser.expressionClasses.variables.SpecificVar;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class UnaryOperation extends AbstractLogicalExpression {
    private final LogicalExpression arg;
    private final String operationSymbol;
    private final boolean isTermOperation;

    public UnaryOperation(LogicalExpression arg, String operationSymbol, boolean isTermOperation) {
        this.arg = arg;
        this.operationSymbol = operationSymbol;
        this.isTermOperation = isTermOperation;
    }

    public LogicalExpression getArg() {
        return arg;
    }

    @Override
    public void getAllVarsImpl(Set<SpecificVar> specificVars) {
        arg.getAllVarsImpl(specificVars);
    }

    @Override
    public void getBoundedVarsImpl(Set<SpecificVar> boundedVars, Set<SpecificVar> freeVars) {
        arg.getBoundedVarsImpl(boundedVars, freeVars);
    }

    @Override
    protected boolean matchWithImplNext(LogicalExpression expression, Map<String, AbstractAny> substitutions) {
        return arg.matchWithImpl(((UnaryOperation)expression).getArg(), substitutions);
    }

    @Override
    protected boolean extractTermAsImplNext(LogicalExpression expression, SpecificVar specificVar, AnyTerm substitution) {
        return arg.extractTermAsImpl(((UnaryOperation)expression).getArg(), specificVar, substitution);
    }

    @Override
    public boolean isTerm() {
        return isTermOperation && arg.isTerm();
    }

    @Override
    public String toString() {
        return "(" + operationSymbol + arg.toString() + ")";
    }

    @Override
    public boolean safeEquals(Object o) {
        UnaryOperation that = (UnaryOperation) o;
        return arg.equals(that.getArg());
    }

    @Override
    public int hashCode() {
        return Objects.hash(arg, operationSymbol);
    }
}
