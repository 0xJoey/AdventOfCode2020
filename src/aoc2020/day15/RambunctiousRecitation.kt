package aoc2020.day15

import AoCDay
import aoc2020.readFromFile

class RambunctiousRecitation: AoCDay {
    val firstTurns = readFromFile("day15")[0]
        .split(",")
        .map(String::toInt)

    override fun part1(): String {
        return playMemory(firstTurns,2020).toString()
    }

    override fun part2(): String {
        return playMemory(firstTurns,30000000).toString()
    }

    private fun playMemory(firstTurns: List<Int>, target: Int): Int {
        val preamble = firstTurns.size

        val numbers = Array(target) { -1 }
        firstTurns.forEachIndexed { i, it ->
            numbers[it] = i+1
        }

        var prevTurn = firstTurns[preamble-1]
        for(i in preamble until target) {
            val lastOccurence = numbers[prevTurn]
            numbers[prevTurn] = i
            prevTurn = if(lastOccurence != -1) {
                i - lastOccurence
            } else {
                0
            }
        }
        return prevTurn
    }
}