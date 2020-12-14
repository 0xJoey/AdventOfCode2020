package day1

import readFromFile

fun day1b(): String {
    val sortedNumbers = readFromFile("day1")
        .map(String::toInt)
        .sorted()

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
                return (a * b * c).toString()
            }
        }
        end--
        if(end == 0) {
            return "error"
        }
    }
}