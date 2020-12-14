package day9

import readFromFile
import java.util.*

fun day9b(): String {
    val preambleSize = 25

    val input = readFromFile("day9")
        .map(String::toLong)
        .toList()

    var brokenSum = 0L

    for(i in (preambleSize) until input.size) {
        val previous = input.subList(i-preambleSize, i)

        val pair = findSum(input[i], previous)
        if(pair.first != -1L) {
            //println("${input[i]} | ${pair.first} + ${pair.second}")
        } else {
            brokenSum = input[i]
        }
    }

    val contSum = findContSum(brokenSum, input)

    return (contSum.min()!! + contSum.max()!!).toString()
}

private fun findContSum(target: Long, list: List<Long>): List<Long> {
    for(i in (0..list.lastIndex)) {
        val sumListOptions = findContSumFromIndex(target, list, i)
        if(sumListOptions.isNotEmpty()) {
            return sumListOptions[0]
        }
    }
    return ArrayList()
}

private fun findContSumFromIndex(target: Long, list: List<Long>, idx: Int): List<List<Long>> {
    val subList = list.subList(idx, list.lastIndex)
    return (1..list.lastIndex-idx)
        .map {
            subList.take(it)
        }
        .filter {
            it.sum() == target
        }
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