package day1

import readFromFile
import java.util.*
import kotlin.system.exitProcess

fun day1a(): String {
    val sortedNumbers = readFromFile("day1")
        .map(String::toInt)
        .sorted()

    var a: Int
    var b: Int
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
                return (a * b).toString()
            }
        }
        end--
    }
}