package aoc2020.day3

import AoCDay
import aoc2020.readFromFile

class TobogganTrajectory: AoCDay {
    private val map = readFromFile("day3")
    private val slopes = listOf(
        1 to 1,
        3 to 1,
        5 to 1,
        7 to 1,
        1 to 2)


    override fun part1(): String {
        return calcTrees(map, slopes[1]).toString()
    }

    override fun part2(): String {

        return slopes.map { calcTrees(map, it) }.reduce{it, acc -> (it*acc)}.toString()
    }

    private fun calcTrees(map: List<String>, slope: Pair<Int,Int>): Long {
        val strLen = map[0].length

        var x = 0
        var y = 0
        var trees = 0L

        while(y < map.size) {
            if(map[y][x] == '#') {
                trees++
            }
            x+=slope.first
            x%=strLen
            y+=slope.second
        }
        return trees
    }

}