package aoc2020.day5

import AoCDay
import aoc2020.readFromFile

class BinaryBoarding: AoCDay {
    private val idList = readFromFile("day5")
        .map(this::calcId).sorted().reversed()

    override fun part1(): String {
        return idList
            .max()
            .toString()
    }

    override fun part2(): String {
        return findMissing(idList).toString()
    }

    private fun calcId(str: String): Int {
        val row = str.substring((0..6))
        val col = str.substring((7..9))
        val rowNo = row.decodeToBinary('B')
        val colNo = col.decodeToBinary('R')
        val id = rowNo * 8 + colNo
        return id
    }

    private fun findMissing(list: List<Int>): Int {
        val range = IntRange(list.min()!!, list.max()!!)
        for(i in range) {
            if(!list.contains(i)) {
                return i
            }
        }
        return -1
    }

    private fun String.decodeToBinary(high: Char): Int {
        val r = this.reversed().mapIndexed { i, c ->
            if(c == high) 1.shl(i) else 0
            }.sum()
        return r
    }
}