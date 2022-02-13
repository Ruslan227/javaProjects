package parser.expressionClasses;

import parser.expressionClasses.any.AbstractAny;
import parser.expressionClasses.any.AnyTerm;
import parser.expressionClasses.variables.SpecificVar;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Zero extends AbstractLogicalExpression {
    public Zero() {}

    @Override
    public String toString() {
        return "0";
    }

    @Override
    public boolean isTerm() {
        return true;
    }

    @Override
    public void getAllVarsImpl(Set<SpecificVar> specificVars) {
    }

    @Override
    public void getBoundedVarsImpl(Set<SpecificVar> boundedVars, Set<SpecificVar> freeVars) {
    }

    @Override
    protected boolean matchWithImplNext(LogicalExpression expression, Map<String, AbstractAny> substitutions) {
        return true;
    }

    @Override
    protected boolean extractTermAsImplNext(LogicalExpression expression, SpecificVar specificVar, AnyTerm substitution) {
        return true;
    }

    @Override
    public boolean substitute(Map<String, LogicalExpression> subByName) {
        return true;
    }

    @Override
    protected boolean safeEquals(Object o) {
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash("0");
    }
}
