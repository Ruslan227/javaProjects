package parser.expressionClasses;

import parser.expressionClasses.any.AbstractAny;
import parser.expressionClasses.any.AnyExpression;
import parser.expressionClasses.any.AnyTerm;
import parser.expressionClasses.any.AnyVar;
import parser.expressionClasses.variables.SpecificVar;

import java.util.Map;
import java.util.Set;

public abstract class AbstractLogicalExpression implements LogicalExpression {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return safeEquals(o);
    }

    @Override
    public boolean matchWithImpl(LogicalExpression expression, Map<String, AbstractAny> substitutions) {
        if (expression.getClass() == AnyTerm.class) {
            AnyTerm anyTerm = (AnyTerm) expression;
            if (substitutions.containsKey(anyTerm.getName())) {
                return substitutions.get(anyTerm.getName()).getSubstitution().equals(this);
            }
            if (!isTerm()) {
                return false;
            }
            substitutions.put(anyTerm.getName(), new AnyTerm(anyTerm.getName(), this));
            return true;
        }
        if (expression.getClass() == AnyExpression.class) {
            AnyExpression anyExpression = (AnyExpression) expression;
            if (substitutions.containsKey(anyExpression.getName())) {
                return substitutions.get(anyExpression.getName()).getSubstitution().equals(this);
            }
            substitutions.put(anyExpression.getName(), new AnyExpression(anyExpression.getName(), this));
            return true;
        }
        if (expression.getClass() == AnyVar.class) {
            AnyVar anyVar = (AnyVar) expression;
            if (substitutions.containsKey(anyVar.getName())) {
                return substitutions.get(anyVar.getName()).getSubstitution().equals(this);
            }
            if (getClass() != SpecificVar.class) {
                return false;
            }
            substitutions.put(anyVar.getName(), new AnyVar(anyVar.getName(), this));
            return true;
        }
        if (getClass() != expression.getClass()) {
            return false;
        }
        return matchWithImplNext(expression, substitutions);
    }

    protected abstract boolean matchWithImplNext(LogicalExpression expression, Map<String, AbstractAny> substitutions);

    @Override
    public boolean extractTermAsImpl(LogicalExpression expression, SpecificVar specificVar, AnyTerm substitution) {
        if (expression.equals(specificVar)) {
            if (!isTerm()) {
                return false;
            }
            if (substitution.getSubstitution() == null) {
                substitution.substitute(this);
                return true;
            }
            return equals(substitution.getSubstitution());
        }
        if (getClass() != expression.getClass()) {
            return false;
        }
        return extractTermAsImplNext(expression, specificVar, substitution);
    }

    protected abstract boolean extractTermAsImplNext(LogicalExpression expression, SpecificVar specificVar, AnyTerm substitution);

    protected abstract boolean safeEquals(Object o);
}
