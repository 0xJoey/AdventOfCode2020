package day18

import readFromFile
import java.lang.RuntimeException

fun main() {
    println(day18b())
}

fun day18b(): String {
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

    var addIndex = parts.indexOf("+")
    while(addIndex > 0) {
        val left = parts[addIndex-1].toLong()
        val right = parts[addIndex+1].toLong()

        var leftPart = listOf<String>()
        if(addIndex-1 != 0) {
            leftPart = parts.subList(0, addIndex-1)
        }

        var rightPart = listOf<String>()
        if(addIndex+1 != parts.lastIndex) {
            rightPart = parts.subList(addIndex+2, parts.size)
        }

        parts = leftPart + listOf((left+right).toString()) + rightPart
        addIndex = parts.indexOf("+")
    }
    return parts.filter { it != "*" }.map(String::toLong).fold(1L) {v, acc -> v*acc}

}