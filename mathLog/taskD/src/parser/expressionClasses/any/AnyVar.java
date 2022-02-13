package parser.expressionClasses.any;

import parser.expressionClasses.LogicalExpression;
import parser.expressionClasses.Var;
import parser.expressionClasses.variables.SpecificVar;

public class AnyVar extends AbstractAny implements Var {
    public AnyVar(String name) {
        super(name);
    }

    public AnyVar(String name, LogicalExpression substitution) {
        super(name, substitution);
    }

    @Override
    protected boolean checkSubstitution(LogicalExpression substitution) {
        return substitution != null && substitution.getClass() == SpecificVar.class;
    }
}
