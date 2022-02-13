package tests;

import detector.MainDetector;
import parser.expressionClasses.variables.Predicate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FinalTests {
    public static final String TESTS_PATH = "/Users/aleksandrslastin/Downloads/TaskC/resources2";

    private static final Pattern patternInput = Pattern.compile("[a-z,_]*in.txt", Pattern.CASE_INSENSITIVE);

    public static void main(String[] args) throws Exception {
        var path = Paths.get(TESTS_PATH);
        run(path);
    }

    private static void run(Path path) throws Exception {
        try (DirectoryStream<Path> pathStream = Files.newDirectoryStream(path)) {
            for (Path p : pathStream) {
                if (Files.isDirectory(p)) {
                    System.out.println("Start testing " + p + " directory");
                    run(p);
                } else {
                    Matcher matcher = patternInput.matcher(p.toString());
                    if (matcher.find())
                        processInputFile(p);
                }
            }
        }
    }

    private static void processInputFile(Path path) throws Exception {
        var input = readFile(path.toString());

        System.out.println(path);

        var actualResult = MainDetector.annotateProof(input);

        String stringTmp = path.toString();
        String clearStringTestFileName = stringTmp.substring(0, stringTmp.length() - 6);
        String stringOutputPath = clearStringTestFileName + "out.txt";

        var wantedResult = readFile(stringOutputPath);

        stringListEquals(wantedResult, actualResult, clearStringTestFileName);
    }

    private static List<String> readFile(String path) {
        var result = new ArrayList<String>();
        try (var reader = new BufferedReader(new FileReader(path))) {
            String s = reader.readLine();
            while (s != null) {
                result.add(s);
                s = reader.readLine();
            }
        } catch (IOException e) {
            System.err.println("error reading file: " + path);
        }
        return result;
    }

    private static List<String> extractFromAnnotated(String annotated) {
        return Arrays.stream(annotated.split("[ ,\\[\\]]"))
                .filter(str -> !str.isEmpty())
                .skip(1)
                .collect(Collectors.toList());
    }

    private static String extractLast(String annotated) {
        List<String> extracted = extractFromAnnotated(annotated);
        return extracted.get(extracted.size() - 1);
    }

    private static void stringListEquals(List<String> expected, List<String> actual, String testName) {
        System.out.println(testName + " started");
        boolean failed = false;
        for (int i = 0; i < expected.size(); i++) {
            List<String> expectedExtracted = extractFromAnnotated(expected.get(i));
            List<String> actualExtracted = extractFromAnnotated(actual.get(i));
            if (i != 0 && expectedExtracted.get(0).equals(actualExtracted.get(0))) {
                switch (expectedExtracted.get(0)) {
                    case "M.P.":
                        int expectedK = Integer.parseInt(expectedExtracted.get(1));
                        int expectedL = Integer.parseInt(expectedExtracted.get(2));
                        int actualK = Integer.parseInt(actualExtracted.get(1));
                        int actualL = Integer.parseInt(actualExtracted.get(2));
                        if (!expectedExtracted.get(3).equals(actualExtracted.get(3))) {
                            break;
                        }
                        if (actualK < expectedK) {
                            continue;
                        }
                        if (actualK == expectedK && actualL <= expectedL) {
                            continue;
                        }
                        break;
                    case "?-intro":
                    case "@-intro":
                        int expectedRow = Integer.parseInt(expectedExtracted.get(1));
                        int actualRow = Integer.parseInt(actualExtracted.get(1));
                        if (!expectedExtracted.get(2).equals(actualExtracted.get(2))) {
                            break;
                        }
                        if (actualRow <= expectedRow && extractLast(actual.get(actualRow)).equals(extractLast(expected.get(expectedRow)))) {
                            continue;
                        }
                }
            }
            if (!expected.get(i).equals(actual.get(i))) {
                failed = true;
                System.err.println("\ttestName: " + testName + "\nexpected: " +
                        expected.get(i) + "\nactual: " + actual.get(i));
                System.err.println();
            }
        }
        if (!failed) {
            System.out.println(testName + " accepted");
        }
    }
}
