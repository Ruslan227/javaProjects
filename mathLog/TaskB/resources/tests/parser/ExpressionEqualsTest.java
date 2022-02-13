package tests.parser;

import org.junit.Assert;
import org.junit.Test;
import parser.Parser;
import parser.expressionClasses.LogicalObject;

public class ExpressionEqualsTest {
    private static Parser p = new Parser();

    @Test(timeout = 20)
    public void test1() {
        String e1 = "A";
        String e2 = "A";
        LogicalObject l1 = p.parse(e1);
        LogicalObject l2 = p.parse(e2);
        Assert.assertEquals(l1, l2);
    }

    @Test(timeout = 20)
    public void test2() {
        String e1 = "(A)";
        String e2 = "A";
        LogicalObject l1 = p.parse(e1);
        LogicalObject l2 = p.parse(e2);
        Assert.assertEquals(l1, l2);
    }

    @Test(timeout = 20)
    public void test3() {
        String e1 = "((A))";
        String e2 = "A";
        LogicalObject l1 = p.parse(e1);
        LogicalObject l2 = p.parse(e2);
        Assert.assertEquals(l1, l2);
    }

    @Test(timeout = 20)
    public void test4() {
        String e1 = "((((((((A | N1))))))))";
        String e2 = "A | N1";
        LogicalObject l1 = p.parse(e1);
        LogicalObject l2 = p.parse(e2);
        Assert.assertEquals(l1, l2);
    }

    @Test(timeout = 20)
    public void test5() {
        String e1 = "(A -> B -> C)";
        String e2 = "(A -> B) -> C";
        LogicalObject l1 = p.parse(e1);
        LogicalObject l2 = p.parse(e2);
        Assert.assertNotEquals(l1, l2);
    }

    @Test(timeout = 20)
    public void test6() {
        String e1 = "A -> (B -> C)";
        String e2 = "A -> B -> C";
        LogicalObject l1 = p.parse(e1);
        LogicalObject l2 = p.parse(e2);
        Assert.assertEquals(l1, l2);
    }

    @Test(timeout = 20)
    public void test7() {
        String e1 = "A & D";
        String e2 = "(((((A&D)))))";
        LogicalObject l1 = p.parse(e1);
        LogicalObject l2 = p.parse(e2);
        Assert.assertEquals(l1, l2);
    }
}
