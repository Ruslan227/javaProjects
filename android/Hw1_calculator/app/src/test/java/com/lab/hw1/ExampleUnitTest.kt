package com.lab.hw1

import com.lab.hw1.parser.Parser
import org.junit.Test



import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExampleUnitTest {

    private val parser = Parser()
    private val EPS:Double = 4.9E-200

    private fun abstractTest(input: String, expectedVal: Double) {
        val r = parser.parse(input)
        assertEquals(expectedVal, r.evaluate(), EPS)
    }

    @Test
    fun test1() {
        abstractTest("1", 1.0)
    }

    @Test
    fun test2() {
        abstractTest("1 + 1", 2.0)
    }

    @Test
    fun test3() {
        abstractTest("41 - 1", 40.0)
    }

    @Test
    fun test4() {
        abstractTest("(4 + 1)", 5.0)
    }

    @Test
    fun test5() {
        abstractTest("(4 - 1)", 3.0)
    }

    @Test
    fun test6() {
        abstractTest("8 * 2", 16.0)
    }

    @Test
    fun test7() {
        abstractTest("44 / 11", 4.0)
    }

    @Test
    fun test8() {
        abstractTest("(4 * 8)", 32.0)
    }

    @Test
    fun test9() {
        abstractTest("(4 / 4)", 1.0)
    }

    @Test
    fun test10() {
        abstractTest("4 + 1 *  90", 94.0)
    }

    @Test
    fun test11() {
        abstractTest("2 * 2 + 2", 6.0)
    }

    @Test
    fun test12() {
        abstractTest("8 * (2 + 3)", 40.0)
    }

    @Test
    fun test13() {
        abstractTest("24 / (2 + 6)", 3.0)
    }

    @Test
    fun test14() {
        abstractTest("(((4 + 1)) *  90)", 450.0)
    }

    @Test
    fun test15() {
        abstractTest("2 * (((((2 + 2)))))", 8.0)
    }


}