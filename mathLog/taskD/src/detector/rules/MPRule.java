package detector.rules;

import detector.MatchStatus;
import detector.Pair;
import parser.expressionClasses.LogicalExpression;
import parser.expressionClasses.binary.Follow;

import java.util.*;

public class MPRule implements Rule {
    private final Map<LogicalExpression, Integer> proved = new HashMap<>();
    private final Map<LogicalExpression, Set<RowedExpression>> futureProved = new HashMap<>();
    private final Map<LogicalExpression, Pair<Integer, Integer>> provedByMP = new HashMap<>();
    int k = -1, l = -1;

    public void putProved(LogicalExpression provedExpression, int row) {
        proved.putIfAbsent(provedExpression, row);
        if (provedExpression instanceof Follow) {
            Follow follow = (Follow) provedExpression;
            Set<RowedExpression> canProve = futureProved.computeIfAbsent(follow.getArg1(), $ -> new HashSet<>());
            RowedExpression rowedArg2 = new RowedExpression(follow.getArg2(), row);
            canProve.add(rowedArg2);

            Integer k = proved.get(follow.getArg1());
            if (k != null) {
                Pair<Integer, Integer> klPrev = provedByMP.get(follow.getArg2());
                if (klPrev == null || klPrev.getFirst() > k) {
                    provedByMP.put(follow.getArg2(), new Pair<>(k, row));
                }
            }
        }
        checkProvedByMP(provedExpression, row);
    }

    private void checkProvedByMP(LogicalExpression provedExpression, int row) {
        if (!futureProved.containsKey(provedExpression)) {
            return;
        }
        for (var rowedProved : futureProved.get(provedExpression)) {
            provedByMP.putIfAbsent(rowedProved.expression, new Pair<>(row, rowedProved.row));
        }
        futureProved.get(provedExpression).clear();
    }

    @Override
    public Pair<MatchStatus, String> match(LogicalExpression expression, int row, Map<LogicalExpression, Integer> proved) {
        Pair<Integer, Integer> pair = provedByMP.get(expression);
        if (pair != null) {
            k = pair.getFirst();
            l = pair.getSecond();
            return new Pair<>(MatchStatus.TRUE, annotate(row));
        }
        return new Pair<>(MatchStatus.FALSE);
    }

    @Override
    public String annotate(int row) {
        return String.format("[%d. M.P. %d, %d]", row, k, l);
    }

    private static class RowedExpression {
        private final LogicalExpression expression;
        private final int row;

        public RowedExpression(LogicalExpression expression, int row) {
            this.expression = expression;
            this.row = row;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RowedExpression that = (RowedExpression) o;
            return Objects.equals(expression, that.expression);
        }

        @Override
        public int hashCode() {
            return Objects.hash(expression);
        }
    }
}
