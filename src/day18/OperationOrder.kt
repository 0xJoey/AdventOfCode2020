package day18

import readFromFile
import java.lang.RuntimeException

fun day18a(): String {
    val exprs = readFromFile("day18")

    return exprs.map(::parse).sum().toString()
}

private fun parse(input: String): Long {
    var expression = input

    var blockBegin = expression.indexOf("(")
    while(blockBegin >= 0) {
        var i = blockBegin+1
        var counter = 1
        var innerExpr = "("
        while(counter > 0) {
            val nextChar = expression[i]
            when(nextChar) {
                ')' -> counter -= 1
                '(' -> counter += 1
            }
            innerExpr += nextChar
            i++
        }
        val blockEnd = i
        //println(innerExpr)
        expression = expression.substring(0, blockBegin) + parse(innerExpr.substring(1,innerExpr.length-1)) + expression.substring(blockEnd)
        blockBegin = expression.indexOf("(")
        //println(expression)
    }

    var parts = expression.split(" ")
    while(parts.size > 1) {
        val left = parts[0].toLong()
        val op = parts[1]
        val right = parts[2].toLong()

        val value = when(op) {
            "+" -> left + right
            "*" -> left * right
            else -> error("Something went wrong")
        }.toString()

        parts = listOf(value) + parts.subList(3, parts.size)
    }
    return parts[0].toLong()

}