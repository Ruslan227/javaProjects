package tests.parser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import parser.tokens.Tokenizer;
import parser.tokens.Tokens;

public class TokenizerTest {
    private static final String expr =
            "!(A | B) -> C"
                    .replace("|-", "⊢")
                    .replace("->", "→");
    private static final Tokenizer tokenizer = new Tokenizer(expr);

    @BeforeAll
    static void beforeAll() {
        System.out.println("===== Starting =====");
    }

    @Test
    public void test1() {
        Assert.assertEquals(tokenizer.getToken(), Tokens.START);
    }

    @Test
    public void test2() {
        tokenizer.nextToken();
        Assert.assertEquals(tokenizer.getToken(), Tokens.OP_NEG);
    }

    @Test
    public void test3() {
        tokenizer.nextToken();
        Assert.assertEquals(Tokens.OPEN_BRACKET, tokenizer.getToken());
    }

    @Test
    public void test4() {
        tokenizer.nextToken();
        Assert.assertEquals(tokenizer.getToken(), Tokens.VAR);
        Assert.assertEquals(tokenizer.getLastVar(), "A");
    }

    @Test
    public void test5() {
        tokenizer.nextToken();
        Assert.assertEquals(tokenizer.getToken(), Tokens.OP_OR);
        Assert.assertEquals(tokenizer.getLastVar(), "A");
    }

    @Test
    public void test6() {
        tokenizer.nextToken();
        Assert.assertEquals(tokenizer.getToken(), Tokens.VAR);
        Assert.assertEquals(tokenizer.getLastVar(), "B");
    }

    @Test
    public void test7() {
        tokenizer.nextToken();
        Assert.assertEquals(tokenizer.getToken(), Tokens.CLOSE_BRACKET);
        Assert.assertEquals(tokenizer.getLastVar(), "B");
    }

    @Test
    public void test8() {
        tokenizer.nextToken();
        Assert.assertEquals(tokenizer.getToken(), Tokens.OP_FOLLOW);
        Assert.assertEquals(tokenizer.getLastVar(), "B");
    }

    @Test
    public void test9() {
        tokenizer.nextToken();
        Assert.assertEquals(tokenizer.getToken(), Tokens.VAR);
        Assert.assertEquals(tokenizer.getLastVar(), "C");
    }


    @AfterAll
    static void afterAll() {
        System.out.println("===== End =====");
    }
}
