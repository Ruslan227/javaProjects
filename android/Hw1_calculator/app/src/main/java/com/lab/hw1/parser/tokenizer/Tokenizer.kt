package com.lab.hw1.parser.tokenizer

import com.lab.hw1.parser.exceptions.ParsingException


class Tokenizer(private val expression: String) {
    private var ind: Int = -1
    var lastConst: Double = 0.0
        private set
    var token: Token = Token.START
        private set

    private fun skipWhiteSpaces() {
        while (ind < expression.length && Character.isWhitespace(expression[ind])) {
            ind++
        }
    }

    private fun parseConst(): Double {
        val addDigits = { argThis: StringBuilder ->
            while (ind < expression.length && expression[ind].isDigit()) {
                argThis.append(expression[ind])
                ind++
            }
        }
        val res = StringBuilder()
        addDigits.invoke(res)
        if (ind < expression.length && expression[ind] == '.') {
            res.append(expression[ind])
            ind++
            addDigits.invoke(res)
        }
        ind--
        return res.toString().toDouble()
    }

    fun nextToken() {
        ind++
        skipWhiteSpaces()

        if (ind >= expression.length) {
            token = Token.END
            return
        }

        when (expression[ind]) {
            '(' -> token = Token.OPEN_BR
            ')' -> token = Token.CLOSE_BR
            '+' -> token = Token.PLUS
            '-' -> token = Token.MINUS
            '*' -> token = Token.MUL
            '/' -> token = Token.DIV

            else -> {
                if (Character.isDigit(expression[ind])) {
                    lastConst = parseConst()
                    token = Token.CONST
                } else
                    throwException("unexpected symbol")
            }
        }
    }

    fun throwException(message: String) {
        val t =
            if (ind + 1 < expression.length)
                ind + 1
            else
                expression.length

        throw ParsingException(
            expression.substring(0, t) + "...| error: $message",
            ind
        )
    }
}