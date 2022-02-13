package parser.expressionClasses;

import parser.expressionClasses.any.AbstractAny;
import parser.expressionClasses.any.AnyTerm;
import parser.expressionClasses.variables.SpecificVar;

import java.util.*;

public interface LogicalExpression {
    boolean isTerm();

    static LogicalExpression extractAnyByName(Set<AbstractAny> anySet, String name, String ruleName) {
        for (AbstractAny any : anySet) {
            if (any.getName().equals(name)) {
                LogicalExpression substitution = any.getSubstitution();
                if (substitution == null) {
                    throw new RuntimeException("Error occurred in " + ruleName);
                }
                return substitution;
            }
        }
        return null;
    }

    default Set<SpecificVar> getAllVars() {
        Set<SpecificVar> specificVars = new HashSet<>();
        getAllVarsImpl(specificVars);
        return specificVars;
    }
    void getAllVarsImpl(Set<SpecificVar> specificVars);

    default Set<SpecificVar> getBoundedVars() {
        Set<SpecificVar> boundedVars = new HashSet<>();
        Set<SpecificVar> freeVars = new HashSet<>();
        getBoundedVarsImpl(boundedVars, freeVars);
        return boundedVars;
    }
    void getBoundedVarsImpl(Set<SpecificVar> boundedVars, Set<SpecificVar> freeVars);

    default Set<AbstractAny> matchWith(LogicalExpression expression) {
        Map<String, AbstractAny> substitutions = new HashMap<>();
        boolean isMatched = matchWithImpl(expression, substitutions);
        return isMatched ? new HashSet<>(substitutions.values()) : null;
    }
    boolean matchWithImpl(LogicalExpression expression, Map<String, AbstractAny> substitutions);

    default LogicalExpression extractTermAs(LogicalExpression expression, SpecificVar specificVar) {
        AnyTerm substitution = new AnyTerm("LOL");
        boolean isExtracted = extractTermAsImpl(expression, specificVar, substitution);
        return isExtracted ?  (substitution.getSubstitution() == null ? new SpecificVar("ANIME") : substitution.getSubstitution()) : null;
    }
    boolean extractTermAsImpl(LogicalExpression expression, SpecificVar specificVar, AnyTerm substitution);

    default Set<SpecificVar> getFreeVars() {
        Set<SpecificVar> free = getAllVars();
        free.removeAll(getBoundedVars());
        return free;
    }
}
