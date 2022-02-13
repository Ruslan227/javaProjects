package parser.expressionClasses;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class Conj extends BinaryOperation implements LogicalObject {

    public Conj(LogicalObject arg1, LogicalObject arg2) {
        super(arg1, arg2, "&");
    }

}
