package aoc2020.day18

import AoCDay
import aoc2020.product
import aoc2020.readFromFile

class OperationOrder: AoCDay {
    private val expressions = readFromFile("day18")

    override fun part1(): String {
        return expressions.map(::parsePart1).sum().toString()
    }

    override fun part2(): String {
        return expressions.map(::parsePart2).sum().toString()
    }

    private fun parsePart1(input: String): Long {
        var expression = input

        var blockBegin = expression.indexOf("(")
        while (blockBegin >= 0) {
            var i = blockBegin + 1
            var counter = 1
            var innerExpr = "("
            while (counter > 0) {
                val nextChar = expression[i]
                when (nextChar) {
                    ')' -> counter -= 1
                    '(' -> counter += 1
                }
                innerExpr += nextChar
                i++
            }
            val blockEnd = i
            expression = expression.substring(0, blockBegin) + parsePart1(
                innerExpr.substring(
                    1,
                    innerExpr.length - 1
                )
            ) + expression.substring(blockEnd)
            blockBegin = expression.indexOf("(")
        }

        var parts = expression.split(" ")
        while (parts.size > 1) {
            val left = parts[0].toLong()
            val op = parts[1]
            val right = parts[2].toLong()

            val value = when (op) {
                "+" -> left + right
                "*" -> left * right
                else -> error("Something went wrong")
            }.toString()

            parts = listOf(value) + parts.subList(3, parts.size)
        }
        return parts[0].toLong()
    }

    private fun parsePart2(input: String): Long {
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
            expression = expression.substring(0, blockBegin) + parsePart2(innerExpr.substring(1,innerExpr.length-1)) + expression.substring(blockEnd)
            blockBegin = expression.indexOf("(")
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
        return parts.filter { it != "*" }.map(String::toLong).product()
    }
}