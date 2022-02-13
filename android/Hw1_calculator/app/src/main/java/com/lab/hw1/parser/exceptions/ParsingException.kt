package com.lab.hw1.parser.exceptions

import java.lang.RuntimeException

open class ParsingException(message: String, ind: Int) : RuntimeException("\n$message\n${getPlace(ind)}") {

}

private fun getPlace(ind: Int): String {
    val res = StringBuilder()
    for (i in 0 until ind + 1) {
        res.append('~')
    }
    res.append("^\n")
    return res.toString()
}
