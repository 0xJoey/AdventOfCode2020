package day15

import readFromFile

fun main() {
    println(day15b());
}

fun day15b(): String {
    val firstTurns = readFromFile("day15")[0]
        .split(",")
        .map(String::toInt)

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