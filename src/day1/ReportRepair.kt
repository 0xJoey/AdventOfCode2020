package day1

import java.util.*
import kotlin.system.exitProcess

fun main() {
    var lineIn: String?

    val numberQueue = PriorityQueue<Int>()

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
            if(b == max) {
                println(a * b)
                exitProcess(0)
            }
        }
        end--
    }
}