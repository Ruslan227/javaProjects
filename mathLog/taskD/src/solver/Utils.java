package solver;

import parser.expressionClasses.LogicalExpression;
import parser.expressionClasses.Zero;
import parser.expressionClasses.unary.Apostrophe;

import java.util.Arrays;
import java.util.stream.Stream;

public class Utils {
    public static boolean isEvenNatural(String number) {
        try {
            int n = Integer.parseInt(number);
            return n % 2 == 0;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static LogicalExpression addApostrophes(LogicalExpression base, int n) {
        for (int i = 0; i < n; i++) {
            base = new Apostrophe(base);
        }
        return base;
    }

    public static LogicalExpression getNumber(int n) {
        return addApostrophes(new Zero(), n);
    }

    public static Stream<String> mergeStreams(Stream<String>... streams) {
        return Arrays.stream(streams).reduce(Stream.empty(), Stream::concat).distinct();
    }
}
