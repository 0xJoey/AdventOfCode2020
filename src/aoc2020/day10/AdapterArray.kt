package aoc2020.day10

import AoCDay
import aoc2020.readFromFile

class AdapterArray: AoCDay {
    private val adapters = readFromFile("day10")
        .map(String::toInt)

    private val lut = HashMap<Int,Long>()

    override fun part1(): String {
        val sortedAdapters = adapters.sorted()
        val diffs = sortedAdapters.indices.drop(1).map {i ->
            sortedAdapters[i] - sortedAdapters[i-1]
        }

        val ones = diffs.count { it==1 }+1
        val threes = diffs.count { it==3 }+1
        return (ones*threes).toString()
    }

    override fun part2(): String {
        val endAdapter = adapters.max()!! + 3
        lut[endAdapter] = 1
        return ((numTransmutations(adapters + endAdapter))(0)).toString()
    }

    private fun numTransmutations(adapters: List<Int>): (Int) -> Long {
        return { curAdapter ->
            if (lut.containsKey(curAdapter)) {
                //println("$curAdapter returns ${memo[curAdapter]!!}")
                lut[curAdapter]!!
            } else {
                val num = adapters
                    .filter { it - curAdapter <= 3 }
                    .filter { it - curAdapter > 0 }
                    .map(numTransmutations(adapters))
                    .sum()
                lut[curAdapter] = num
                num
            }
        }
    }

}