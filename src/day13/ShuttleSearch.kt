package day13

import readFromFile

fun day13a(): String {
    val input = readFromFile("day13")
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