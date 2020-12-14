package day13

import readFromFile

fun day13b(): String {
    val input = readFromFile("day13")

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
        .fold(1L) { acc, int -> acc*int.second }

    var time = busLines.first().second

    (1 until busLines.size).forEach {i ->
        val stepSize = busLines
            .take(i)
            .fold(1L) { acc, int -> acc*int.second }

        time = (time..product step stepSize)
            .first {
                (it + busLines[i].first) % busLines[i].second == 0L
            }
    }
    return time.toString()
}