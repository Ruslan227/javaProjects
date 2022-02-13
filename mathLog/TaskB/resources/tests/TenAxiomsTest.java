package tests;

import detector.AxiomChecker;
import detector.Mode;
import detector.Sign;
import org.junit.Assert;
import org.junit.Test;
import parser.Parser;
import parser.expressionClasses.LogicalObject;

public class TenAxiomsTest {
    private static Parser p = new Parser();
    private static AxiomChecker axiomChecker = new AxiomChecker();


//                             Axiom 1

    @Test(timeout = 40)
    public void test1() {
        String e = "A -> B -> A";
        LogicalObject l = p.parse(e);
        Sign sign = axiomChecker.detectAxiom(l);

        Assert.assertEquals(sign.getMode(), Mode.AX);
        Assert.assertEquals(sign.getNum(), 1);
    }

    @Test(timeout = 40)
    public void test2() {
        String e = "(A -> B) -> A";
        LogicalObject l = p.parse(e);
        Sign sign = axiomChecker.detectAxiom(l);

        Assert.assertEquals(sign.getMode(), Mode.ERROR);
    }

    @Test(timeout = 40)
    public void test3() {
        String e = "(A | B) -> (C | S & B) -> (A | B)";
        LogicalObject l = p.parse(e);
        Sign sign = axiomChecker.detectAxiom(l);

        Assert.assertEquals(sign.getMode(), Mode.AX);
        Assert.assertEquals(sign.getNum(), 1);
    }

    @Test(timeout = 40)
    public void test4() {
        String e = "A -> A -> B -> B -> A -> A";
        LogicalObject l = p.parse(e);
        Sign sign = axiomChecker.detectAxiom(l);

        Assert.assertEquals(sign.getMode(), Mode.ERROR);
    }

    @Test(timeout = 40)
    public void test5() {
        String e = "(A -> A) -> (B -> B) -> (A -> A)";
        LogicalObject l = p.parse(e);
        Sign sign = axiomChecker.detectAxiom(l);

        Assert.assertEquals(sign.getMode(), Mode.AX);
        Assert.assertEquals(sign.getNum(), 1);
    }

    @Test(timeout = 40)
    public void test6() {
        String e = "((A | C) -> (A & D)) -> (B -> B) -> ((A | C) -> (A & D))";
        LogicalObject l = p.parse(e);
        Sign sign = axiomChecker.detectAxiom(l);

        Assert.assertEquals(sign.getMode(), Mode.AX);
        Assert.assertEquals(sign.getNum(), 1);
    }

//                                  Axiom 2

    @Test(timeout = 40)
    public void test7() {
        String e = "(A -> B) -> (A -> B -> C) -> (A -> C)";
        LogicalObject l = p.parse(e);
        Sign sign = axiomChecker.detectAxiom(l);

        Assert.assertEquals(sign.getMode(), Mode.AX);
        Assert.assertEquals(sign.getNum(), 2);
    }

    @Test(timeout = 40)
    public void test8() {
        String e = "(A -> B) -> A -> B -> C -> (A -> C)";
        LogicalObject l = p.parse(e);
        Sign sign = axiomChecker.detectAxiom(l);

        Assert.assertEquals(sign.getMode(), Mode.ERROR);
    }

    @Test(timeout = 40)
    public void test9() {
        String e = "(A -> A -> B) -> (A -> (A -> B) -> C) -> (A -> C)";
        LogicalObject l = p.parse(e);
        Sign sign = axiomChecker.detectAxiom(l);

        Assert.assertEquals(sign.getMode(), Mode.AX);
        Assert.assertEquals(sign.getNum(), 2);
    }

    @Test(timeout = 40)
    public void test10() {
        String e = "((A & F) -> (C | (D & K))) -> ((A & F) -> (C | (D & K)) -> C) -> ((A & F) -> C)";
        LogicalObject l = p.parse(e);
        Sign sign = axiomChecker.detectAxiom(l);

        Assert.assertEquals(sign.getMode(), Mode.AX);
        Assert.assertEquals(sign.getNum(), 2);
    }

    //                                  Axiom 3
    @Test(timeout = 40)
    public void test11() {
        String e = "A -> B -> A & B";
        LogicalObject l = p.parse(e);
        Sign sign = axiomChecker.detectAxiom(l);

        Assert.assertEquals(sign.getMode(), Mode.AX);
        Assert.assertEquals(sign.getNum(), 3);
    }

    @Test(timeout = 40)
    public void test12() {
        String e = "(A -> A -> A) -> B -> (A -> A -> A) & B";
        LogicalObject l = p.parse(e);
        Sign sign = axiomChecker.detectAxiom(l);

        Assert.assertEquals(sign.getMode(), Mode.AX);
        Assert.assertEquals(sign.getNum(), 3);
    }

    @Test(timeout = 40)
    public void test13() {
        String e = "(A | (C & D)) -> ((B | C) & !(K & Z)) -> (A | (C & D)) & ((B | C) & !(K & Z))";
        LogicalObject l = p.parse(e);
        Sign sign = axiomChecker.detectAxiom(l);

        Assert.assertEquals(sign.getMode(), Mode.AX);
        Assert.assertEquals(sign.getNum(), 3);
    }

    //                                  Axiom 4
    @Test(timeout = 40)
    public void test14() {
        String e = "A & B -> A";
        LogicalObject l = p.parse(e);
        Sign sign = axiomChecker.detectAxiom(l);

        Assert.assertEquals(sign.getMode(), Mode.AX);
        Assert.assertEquals(sign.getNum(), 4);
    }

    @Test(timeout = 40)
    public void test15() {
        String e = "((A | (C & D)) & ((B | C) & !(K & Z))) & B -> ((A | (C & D)) & ((B | C) & !(K & Z)))";
        LogicalObject l = p.parse(e);
        Sign sign = axiomChecker.detectAxiom(l);

        Assert.assertEquals(sign.getMode(), Mode.AX);
        Assert.assertEquals(sign.getNum(), 4);
    }

    //                                  Axiom 5

    @Test(timeout = 40)
    public void test16() {
        String e = "A & B -> B";
        LogicalObject l = p.parse(e);
        Sign sign = axiomChecker.detectAxiom(l);

        Assert.assertEquals(sign.getMode(), Mode.AX);
        Assert.assertEquals(sign.getNum(), 5);
    }

    @Test(timeout = 40)
    public void test17() {
        String e = "A & ((A | (C & D)) & ((B | C) & !(K & Z))) -> ((A | (C & D)) & ((B | C) & !(K & Z)))";
        LogicalObject l = p.parse(e);
        Sign sign = axiomChecker.detectAxiom(l);

        Assert.assertEquals(sign.getMode(), Mode.AX);
        Assert.assertEquals(sign.getNum(), 5);
    }

    //                                  Axiom 6

    @Test(timeout = 40)
    public void test18() {
        String e = "A -> A | B";
        LogicalObject l = p.parse(e);
        Sign sign = axiomChecker.detectAxiom(l);

        Assert.assertEquals(sign.getMode(), Mode.AX);
        Assert.assertEquals(sign.getNum(), 6);
    }

    @Test(timeout = 40)
    public void test19() {
        String e = "(A | (C & D)) ->  (A | (C & D)) | B";
        LogicalObject l = p.parse(e);
        Sign sign = axiomChecker.detectAxiom(l);

        Assert.assertEquals(sign.getMode(), Mode.AX);
        Assert.assertEquals(sign.getNum(), 6);
    }

    //                                  Axiom 7

    @Test(timeout = 40)
    public void test20() {
        String e = "B -> A | B";
        LogicalObject l = p.parse(e);
        Sign sign = axiomChecker.detectAxiom(l);

        Assert.assertEquals(sign.getMode(), Mode.AX);
        Assert.assertEquals(sign.getNum(), 7);
    }

    @Test(timeout = 40)
    public void test21() {
        String e = "(A | (C & D)) -> B | (A | (C & D))";
        LogicalObject l = p.parse(e);
        Sign sign = axiomChecker.detectAxiom(l);

        Assert.assertEquals(sign.getMode(), Mode.AX);
        Assert.assertEquals(sign.getNum(), 7);
    }

    //                                  Axiom 8

    @Test(timeout = 40)
    public void test22() {
        String e = "(A -> C) -> (B -> C) -> (A | B -> C)";
        LogicalObject l = p.parse(e);
        Sign sign = axiomChecker.detectAxiom(l);

        Assert.assertEquals(sign.getMode(), Mode.AX);
        Assert.assertEquals(sign.getNum(), 8);
    }

    @Test(timeout = 40)
    public void test23() {
        String e = "((C & D | T) -> !(K & Z)) -> (B -> !(K & Z)) -> ((C & D | T) | B -> !(K & Z))";
        LogicalObject l = p.parse(e);
        Sign sign = axiomChecker.detectAxiom(l);

        Assert.assertEquals(sign.getMode(), Mode.AX);
        Assert.assertEquals(sign.getNum(), 8);
    }

    //                                  Axiom 9

    @Test(timeout = 40)
    public void test24() {
        String e = "(A -> B) -> (A -> !B) -> !A";
        LogicalObject l = p.parse(e);
        Sign sign = axiomChecker.detectAxiom(l);

        Assert.assertEquals(sign.getMode(), Mode.AX);
        Assert.assertEquals(sign.getNum(), 9);
    }

    @Test(timeout = 40)
    public void test25() {
        String e = "((A -> !B) -> !(C & D | T)) -> ((A -> !B) -> !!(C & D | T)) -> !(A -> !B)";
        LogicalObject l = p.parse(e);
        Sign sign = axiomChecker.detectAxiom(l);

        Assert.assertEquals(sign.getMode(), Mode.AX);
        Assert.assertEquals(sign.getNum(), 9);
    }

    //                                  Axiom 10

    @Test(timeout = 40)
    public void test26() {
        String e = "A -> !A -> B";
        LogicalObject l = p.parse(e);
        Sign sign = axiomChecker.detectAxiom(l);

        Assert.assertEquals(sign.getMode(), Mode.AX);
        Assert.assertEquals(sign.getNum(), 10);
    }

    @Test(timeout = 40)
    public void test27() {
        String e = "((A -> !B) -> !(C & D | T)) -> !((A -> !B) -> !(C & D | T)) -> G10";
        LogicalObject l = p.parse(e);
        Sign sign = axiomChecker.detectAxiom(l);

        Assert.assertEquals(sign.getMode(), Mode.AX);
        Assert.assertEquals(sign.getNum(), 10);
    }
}
