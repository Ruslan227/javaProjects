package parser.expressionClasses.any;

import parser.expressionClasses.LogicalExpression;

public class AnyTerm extends AbstractAny {
    public AnyTerm(String name) {
        super(name);
    }

    public AnyTerm(String name, LogicalExpression substitution) {
        super(name, substitution);
    }

    @Override
    protected boolean checkSubstitution(LogicalExpression substitution) {
        return substitution != null && substitution.isTerm();
    }
}
