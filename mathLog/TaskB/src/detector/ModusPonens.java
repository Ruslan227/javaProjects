package detector;

import parser.expressionClasses.FollowExpr;
import parser.expressionClasses.LogicalObject;

import java.util.*;

public class ModusPonens {
    private final Map<LogicalObject, HashSet<LogicalObject>> map1 = new HashMap<>();
    private final Map<LogicalObject, LogicalObject> map2 = new HashMap<>();
    private final HashSet<LogicalObject> correctObjects = new HashSet<>();

    public Sign checkMP(LogicalObject obj) {
        LogicalObject value = map2.get(obj);
        if (value != null) {
            putInMap1(obj);
            return new Sign(new FollowExpr(value, obj), value);
        }
        return new Sign();
    }

    // if obj is correct
    public void putInMap1(LogicalObject obj) {
        correctObjects.add(obj);
        if (AxiomChecker.isFollow(obj)) {
            FollowExpr f1 = (FollowExpr) obj;
            if (correctObjects.contains(f1.getArg1())) {
                map2.put(f1.getArg2(), f1.getArg1());
            }

            if (map1.get(f1.getArg1()) == null) {
                map1.put(f1.getArg1(), new HashSet<>(Collections.singletonList(f1.getArg2())));
            } else {
                map1.get(f1.getArg1()).add(f1.getArg2());
            }
        }
        putInMap2(obj);
    }

    private void putInMap2(LogicalObject obj) {
        var val = map1.get(obj);
        if (val != null) {
            for (var expr : val) {
                map2.put(expr, obj);
            }
        }
    }

}