package day1

import java.util.*
import kotlin.system.exitProcess

fun main() {
    val numberQueue = PriorityQueue<Int>()

    var lineIn: String?
    while(true) {
        lineIn = readLine()
        if(lineIn.isNullOrEmpty()) {
            break
        } else {
            numberQueue.add(lineIn.toInt())
        }
    }

    var sortedNumbers = ArrayList<Int>();

    while(!numberQueue.isEmpty()) {
        sortedNumbers.add(numberQueue.remove())
    }

    var a: Int
    var b: Int
    var c: Int
    var max: Int
    var end = sortedNumbers.size-1
    while(true) {
        a = sortedNumbers[end]
        b = 0
        max = 2020-a
        var i = 0
        while(b <= max) {
            b = sortedNumbers[i]
            i++
            c = 2020-a-b
            if(sortedNumbers.contains(c)) {
                println(a * b * c)
                exitProcess(0)
            }
        }
        end--
        if(end == 0) {
            exitProcess(1)
        }
    }
}