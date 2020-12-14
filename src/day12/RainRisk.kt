package day12

import readFromFile
import java.lang.Math.abs

fun day12a(): String {

    readFromFile("day12")
        .forEach(::move)

    return (abs(curPos.first)+abs(curPos.second)).toString()
}

private var curDir = 1
private var curPos = Pair(0L, 0L)

private fun move(input: String) {
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
    //println("[$op$arg] \t $curPos with rotation $curDir")
}