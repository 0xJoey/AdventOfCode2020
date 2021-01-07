package aoc2020.day1

import AoCDay
import aoc2020.readFromFile

class ReportRepair: AoCDay {
    private val sortedNumbers: List<Int> = readFromFile("day1")
        .map(String::toInt)
        .sorted()

    override fun part1(): String {
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

    override fun part2(): String {
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
}