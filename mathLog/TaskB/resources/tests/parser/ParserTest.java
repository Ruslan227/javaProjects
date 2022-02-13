package tests.parser;

import org.junit.Assert;
import org.junit.Test;
import parser.Parser;


public class ParserTest {
    private static Parser p = new Parser();

    @Test(timeout = 100)
    public void test1() {
        String expr = "A";
        Assert.assertEquals("A", p.parse(expr).toString());
    }

    @Test(timeout = 100)
    public void test2() {
        String expr = "A | B";
        Assert.assertEquals("(A | B)", p.parse(expr).toString());
    }

    @Test(timeout = 100)
    public void test3() {
        String expr = "A & B";
        Assert.assertEquals("(A & B)", p.parse(expr).toString());
    }

    @Test(timeout = 100)
    public void test4() {
        String expr = "A -> B";
        Assert.assertEquals("(A -> B)", p.parse(expr).toString());
    }

    @Test(timeout = 100)
    public void test5() {
        String expr = "(A | B)";
        Assert.assertEquals("(A | B)", p.parse(expr).toString());
    }

    @Test(timeout = 100)
    public void test6() {
        String expr = "(A & B)";
        Assert.assertEquals("(A & B)", p.parse(expr).toString());
    }

    @Test(timeout = 100)
    public void test7() {
        String expr = "(A -> B)";
        Assert.assertEquals("(A -> B)", p.parse(expr).toString());
    }

    @Test(timeout = 100)
    public void test8() {
        String expr = "(A | B) -> (C & D)";
        Assert.assertEquals("((A | B) -> (C & D))", p.parse(expr).toString());
    }

    @Test(timeout = 100)
    public void test9() {
        String expr = "(A | B) -> (C & D) -> (A & B) -> (C | D)";
        Assert.assertEquals("((A | B) -> ((C & D) -> ((A & B) -> (C | D))))", p.parse(expr).toString());
    }

    @Test(timeout = 100)
    public void test10() {
        String expr = "A & (C | ((T | Z) & K))";
        Assert.assertEquals("(A & (C | ((T | Z) & K)))", p.parse(expr).toString());
    }

    @Test(timeout = 100)
    public void test11() {
        String expr = "(A & (C | ((T | Z) & K)))";
        Assert.assertEquals("(A & (C | ((T | Z) & K)))", p.parse(expr).toString());
    }

    @Test(timeout = 100)
    public void test12() {
        String expr = "(T | Z) & K";
        Assert.assertEquals("((T | Z) & K)", p.parse(expr).toString());
    }

    @Test(timeout = 100)
    public void test13() {
        String expr = "((T | Z) & K)";
        Assert.assertEquals("((T | Z) & K)", p.parse(expr).toString());
    }

    @Test(timeout = 100)
    public void test14() {
        String expr = "(A | (A & B))    ->  C";
        Assert.assertEquals("((A | (A & B)) -> C)", p.parse(expr).toString());
    }

    @Test(timeout = 100)
    public void test15() {
        String expr = "((A | (A & B))    ->  C)";
        Assert.assertEquals("((A | (A & B)) -> C)", p.parse(expr).toString());
    }

}
