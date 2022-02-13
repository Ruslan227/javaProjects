package parser.expressionClasses;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class Dis extends BinaryOperation implements LogicalObject {

    public Dis(LogicalObject arg1, LogicalObject arg2) {
        super(arg1, arg2, "|");
    }

}
