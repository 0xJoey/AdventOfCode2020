package day9

import readFromFile

fun day9a(): String {
    val preambleSize = 25

    val input = readFromFile("day9")
        .map(String::toLong)
        .toList()

    for(i in (preambleSize) until input.size) {
        val previous = input.subList(i-preambleSize, i)

        val pair = findSum(input[i], previous)
        if(pair.first != -1L) {
            //println("${input[i]} | ${pair.first} + ${pair.second}")
        } else {
            return input[i].toString()
        }
    }
    return "error"
}

private fun findSum(target: Long, list: List<Long>): Pair<Long, Long> {
    val sorted = list.sorted()

    for(a in sorted) {
        val max = target-a
        if(list.contains(max)) {
            return Pair(a,max)
        }
    }
    return Pair(-1L,-1L)
}