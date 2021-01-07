package aoc2020.day24

import AoCDay
import aoc2020.readFromFile

class LobbyLayout: AoCDay {
    val lines = readFromFile("day24")
    private val initialBlackTiles = lines
        .map{
            this.parseLine(it)
        }
        .groupBy { it }
        .filter { (_, v) ->
            v.size % 2 == 1
        }
        .map { (k,_) -> k }

    companion object {
        val ADJACENT = arrayOf(
            -1 to 0,
            -1 to 1,
            0 to 1,
            1 to 0,
            1 to -1,
            0 to -1
        )
    }

    override fun part1(): String {
        return initialBlackTiles.size.toString()
    }

    override fun part2(): String {
        var blackTiles = initialBlackTiles
        repeat(100) { i ->
            blackTiles = blackTiles
                .flatMap { (fromX, fromY) ->
                    ADJACENT.map { (adjX, adjY) ->
                        (adjX+fromX to adjY+fromY)
                    }
                }
                .groupBy { it }
                .filter { (tile, adj) ->
                    when {
                        adj.size == 2 -> true
                        adj.size == 1 -> tile in blackTiles
                        else -> false
                    }
                }
                .map { (t, _) -> t }
            //println("Day ${i+1}: ${blackTiles.size}")
        }

        return blackTiles.size.toString()
    }

    private fun parseLine(str: String, fromPosition: Pair<Int, Int> = Pair(0,0)): Pair<Int, Int> {
        if(str.isEmpty()) return fromPosition

        val (x,y) = fromPosition

        return when {
            str.startsWith("e") -> parseLine(str.drop(1), x+1 to y)
            str.startsWith("se") -> parseLine(str.drop(2), x+1 to y-1)
            str.startsWith("sw") -> parseLine(str.drop(2), x to y-1)
            str.startsWith("w") -> parseLine(str.drop(1), x-1 to y)
            str.startsWith("nw") -> parseLine(str.drop(2), x-1 to y+1)
            str.startsWith("ne") -> parseLine(str.drop(2), x to y+1)
            else -> error("Not recognized: $str")
        }
    }

    private fun IntRange.expand(n: Int = 1): IntRange {
        return (this.first-n..this.last+n)
    }

}