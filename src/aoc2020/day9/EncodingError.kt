package aoc2020.day9

import AoCDay
import aoc2020.readFromFile

class EncodingError: AoCDay {
    private val preambleSize = 25
    val input = readFromFile("day9")
        .map(String::toLong)
        .toList()
    private val invalidSumIndex: Long by lazy {
        input[(preambleSize until input.size).firstOrNull { i ->
            val previous = input.subList(i-preambleSize, i)

            val pair = findSum(input[i], previous)
            pair.first == -1L
        }!!]
    }

    override fun part1(): String {
        return invalidSumIndex.toString()
    }

    override fun part2(): String {
        val contSum = findContSum(invalidSumIndex, input)

        return (contSum.min()!! + contSum.max()!!).toString()
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

    private fun findContSum(target: Long, list: List<Long>): List<Long> {
        for(i in (0..list.lastIndex)) {
            val sumListOptions = findContSumFromIndex(target, list, i)
            if(sumListOptions.isNotEmpty()) {
                return sumListOptions[0]
            }
        }
        return listOf()
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

}