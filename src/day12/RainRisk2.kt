package day12

import readFromFile
import java.lang.Math.abs

fun day12b(): String {

    readFromFile("day12")
        .forEach(::move)

    //println(shipPos)
    return (abs(shipPos.first)+abs(shipPos.second)).toString()
}

private var shipPos = Pair(0L, 0L)
private var waypointPos = Pair(10L, 1L)

private fun move(input: String) {
    val op = input[0]
    val arg = input.substring(1).toInt()
    when(op) {
        'N' -> {
            waypointPos = Pair(waypointPos.first, waypointPos.second+arg)
        }
        'E' -> {
            waypointPos = Pair(waypointPos.first+arg, waypointPos.second)
        }
        'S' -> {
            waypointPos = Pair(waypointPos.first, waypointPos.second-arg)
        }
        'W' -> {
            waypointPos = Pair(waypointPos.first-arg, waypointPos.second)
        }
        'L' -> {
            repeat(arg/90) {
                val x = waypointPos.first
                val y = waypointPos.second
                waypointPos = Pair(-y, x)
            }
        }
        'R' -> {
            repeat(arg/90) {
                val x = waypointPos.first
                val y = waypointPos.second
                waypointPos = Pair(y, -x)
            }
        }
        'F' -> {
            val movement = Pair(waypointPos.first*arg, waypointPos.second*arg)
            shipPos = Pair(shipPos.first+movement.first, shipPos.second+movement.second)
        }
    }
    //println("[$op$arg] \t $shipPos with waypoint $waypointPos")
}