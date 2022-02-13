package com.lab.hw1.parser

import com.lab.hw1.parser.expressionClasses.*
import com.lab.hw1.parser.tokenizer.Token
import com.lab.hw1.parser.tokenizer.Tokenizer

class Parser {
    private lateinit var tokenizer: Tokenizer

    public fun parse(expression: String): ExpressionClass {
        tokenizer = Tokenizer(expression)
        tokenizer.nextToken()
        return parseSumSub()
    }

    private fun parseSumSub(): ExpressionClass {
        var res = parseMulDiv()
        while (tokenizer.token == Token.PLUS || tokenizer.token == Token.MINUS) {
            if (tokenizer.token == Token.PLUS) {
                tokenizer.nextToken()
                res = Sum(res, parseMulDiv())
            }
            else if (tokenizer.token == Token.MINUS) {
                tokenizer.nextToken()
                res = Sub(res, parseMulDiv())
            }
            else
                break
        }
        return res
    }

    private fun parseMulDiv(): ExpressionClass {
        var res = parseUnary()
        while (tokenizer.token == Token.MUL || tokenizer.token == Token.DIV) {
            res = if (tokenizer.token == Token.MUL) {
                tokenizer.nextToken()
                Mul(res, parseUnary())
            }
            else if (tokenizer.token == Token.DIV) {
                tokenizer.nextToken()
                Div(res, parseUnary())
            }
            else
                break
        }
        return res
    }

    private fun parseUnary(): ExpressionClass {
        lateinit var res: ExpressionClass
        when (tokenizer.token) {
            Token.OPEN_BR -> {
                tokenizer.nextToken()
                res = parseSumSub()
                if (tokenizer.token != Token.CLOSE_BR)
                    tokenizer.throwException("expected closing bracket")
            }
            Token.CONST -> {
                res = Const(tokenizer.lastConst)
            }
            else -> {
                tokenizer.throwException("unexpected token")
            }
        }
        tokenizer.nextToken()
        return res
    }
}


















