package aoc2020.day13

import AoCDay
import aoc2020.product
import aoc2020.readFromFile

class ShuttleSearch: AoCDay {
    private val input = readFromFile("day13")

    override fun part1(): String {
        val timestamp = input[0].toInt()
        val buses = input[1].split(",")
            .filter { it != "x" }
            .map(String::toInt)

        val times = (timestamp..(timestamp+buses.min()!!))
            .map {time ->
                buses.filter {bus ->
                    time % bus == 0
                }.map {
                    Pair(time-timestamp, it)
                }
            }
            .filter {
                it.isNotEmpty()
            }
            .flatten()
            .map {
                it.first*it.second
            }
        return times.first().toString()
    }

    override fun part2(): String {
        val busLines = input[1]
            .split(",")
            .withIndex()
            .filter { (_, bus) ->
                bus != "x"
            }
            .map { (i, it) ->
                Pair(i, it.toLong())
            }

        val product = busLines
            .map { (_, v) -> v }
            .product()

        var time = busLines.first().second

        (1 until busLines.size).forEach {i ->
            val stepSize = busLines
                .take(i)
                .map { (_, v) -> v }
                .product()

            time = (time..product step stepSize)
                .first {
                    (it + busLines[i].first) % busLines[i].second == 0L
                }
        }
        return time.toString()
    }

}