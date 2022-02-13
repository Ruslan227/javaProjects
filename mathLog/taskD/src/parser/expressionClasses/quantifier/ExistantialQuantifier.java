package parser.expressionClasses.quantifier;

import parser.expressionClasses.LogicalExpression;
import parser.expressionClasses.Var;
import parser.expressionClasses.variables.SpecificVar;

public class ExistantialQuantifier extends Quantifier {
    public ExistantialQuantifier(Var var, LogicalExpression expr) {
        super(var, expr, "?");
    }
}
