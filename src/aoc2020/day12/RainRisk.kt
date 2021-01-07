package aoc2020.day12

import AoCDay
import aoc2020.readFromFile
import java.lang.Math.abs

class RainRisk: AoCDay {
    private val lines = readFromFile("day12")

    override fun part1(): String {
        var curDir = 1
        var curPos = Pair(0L, 0L)
        lines.forEach { input ->
            val op = input[0]
            val arg = input.substring(1).toInt()
            when(op) {
                'N' -> {
                    curPos = Pair(curPos.first, curPos.second+arg)
                }
                'E' -> {
                    curPos = Pair(curPos.first+arg, curPos.second)
                }
                'S' -> {
                    curPos = Pair(curPos.first, curPos.second-arg)
                }
                'W' -> {
                    curPos = Pair(curPos.first-arg, curPos.second)
                }
                'L' -> {
                    curDir -= arg/90
                    curDir = Math.floorMod(curDir, 4)
                }
                'R' -> {
                    curDir += arg/90
                    curDir = Math.floorMod(curDir, 4)
                }
                'F' -> {
                    when(curDir) {
                        0 -> {
                            curPos = Pair(curPos.first, curPos.second+arg)
                        }
                        1 -> {
                            curPos = Pair(curPos.first+arg, curPos.second)
                        }
                        2 -> {
                            curPos = Pair(curPos.first, curPos.second-arg)
                        }
                        3 -> {
                            curPos = Pair(curPos.first-arg, curPos.second)
                        }
                    }
                }
            }
        }
        return (kotlin.math.abs(curPos.first) + kotlin.math.abs(curPos.second)).toString()
    }

    override fun part2(): String {
        var shipPos = Pair(0L, 0L)
        var waypointPos = Pair(10L, 1L)
        lines.forEach { input ->
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
        }
        return (abs(shipPos.first)+abs(shipPos.second)).toString()
    }
}