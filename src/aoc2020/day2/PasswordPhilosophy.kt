package aoc2020.day2

import AoCDay
import aoc2020.readFromFile

class PasswordPhilosophy: AoCDay {
    private val lines = readFromFile("day2")
    override fun part1(): String {
        return lines.count(this::verifyPart1).toString()
    }

    override fun part2(): String {
        return lines.count(this::verifyPart2).toString()
    }

    private fun verifyPart1(input: String): Boolean {
        var step = input.split("-")

        val min = step[0].toInt()
        step = step[1].split(" ")
        val max = step[0].toInt()
        val key = step[1][0]
        val password = step[2]

        val repeats = password.count { it == key }
        return repeats in (min..max)
    }

    private fun verifyPart2(input: String): Boolean {
        var step = input.split("-")

        val pos1 = step[0].toInt()-1
        step = step[1].split(" ")
        val pos2 = step[0].toInt()-1
        val key = step[1][0]
        val password = step[2]


        return (password[pos1] == key).xor((password[pos2] == key))
    }

}