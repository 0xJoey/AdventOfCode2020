package day15

import readFromFile

fun day15b(): String {
    val firstTurns = readFromFile("day15")[0]
        .split(",")
        .map(String::toInt)

    return playMemory(firstTurns,30000000).toString()
}

private fun playMemory(firstTurns: List<Int>, target: Int): Int {
    val preamble = firstTurns.size

    val numbers = HashMap<Int, Pair<Int,Int>>(target)
    firstTurns
        .withIndex()
        .map {(i, it) ->
            Pair(it, Pair(i+1, -1))
        }
        .forEach {
            numbers[it.first] = it.second
        }

    var prevTurn = firstTurns[preamble-1]
    for(i in preamble until target) {
        if(numbers[prevTurn]!!.second != -1) {
            val previous = numbers[prevTurn]!!
            val newValue = previous.first - previous.second
            prevTurn = newValue
            numbers[newValue] = Pair(i+1, numbers[newValue]?.first ?: -1)
        } else {
            prevTurn = 0
            numbers[0] = Pair(i+1, numbers[0]?.first ?: -1)
        }
    }
    return prevTurn
}