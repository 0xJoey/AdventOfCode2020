package aoc2020

import AoCDay
import aoc2020.day1.*
import aoc2020.day2.*
import aoc2020.day3.*
import aoc2020.day4.*
import aoc2020.day5.*
import aoc2020.day6.*
import aoc2020.day7.*
import aoc2020.day8.*
import aoc2020.day9.*
import aoc2020.day10.*
import aoc2020.day11.*
import aoc2020.day12.*
import aoc2020.day13.*
import aoc2020.day14.*
import aoc2020.day15.*
import aoc2020.day16.*
import aoc2020.day17.*
import aoc2020.day18.*
import aoc2020.day19.*
import aoc2020.day20.*
import aoc2020.day21.*
import aoc2020.day22.*
import aoc2020.day23.*
import aoc2020.day24.*
import aoc2020.day25.*
import aoc2020.helpers.Companion.testSymbol
import java.io.File

class helpers {
    companion object {
        var testSymbol: (Boolean) -> String = {bool ->
            if(bool) "✔️" else "❌"
        }
    }
}

fun readFromFile(day: String): List<String> {
    return File("./src/aoc2020/$day/in.txt")
        .readLines()
        .map(String::trim)
}

fun readAnswers(day: String): Pair<String, String> {
    val answers = File("./src/aoc2020/$day/out.txt")
        .readLines()
        .map(String::trim)
    return Pair(answers[0], answers[1])
}

fun runTests() {
    val beginAllDays = System.nanoTime()
    runDay("day1", ReportRepair())
    runDay("day2", PasswordPhilosophy())
    runDay("day3", TobogganTrajectory())
    runDay("day4", PassportProcessing())
    runDay("day5", BinaryBoarding())
    runDay("day6", CustomCustoms())
    runDay("day7", HandyHaversacks())
    runDay("day8", HandheldHalting())
    runDay("day9", EncodingError())
    runDay("day10", AdapterArray())
    runDay("day11", SeatingSystem())
    runDay("day12", RainRisk())
    runDay("day13", ShuttleSearch())
    runDay("day14", DockingData())
    runDay("day15", RambunctiousRecitation())
    runDay("day16", TicketTranslation())
    runDay("day17", ConwayCubes())
    runDay("day18", OperationOrder())
    runDay("day19", MonsterMessages())
    runDay("day20", JurassicJigsaw())
    runDay("day21", AllergenAssessment())
    runDay("day22", CrabCombat2())
    runDay("day23", CrabCups())
    runDay("day24", LobbyLayout())
    runDay("day25", ComboBreaker())

    println("## Summary ##")
    println("All days total: ${roundTime(beginAllDays)}")
}

fun runDay(name: String, day: AoCDay) {
    runDay(name, day::part1, day::part2)
}

fun runDay(name: String, part1: () -> String, part2: () -> String) {
    val beginTotal = System.nanoTime()
    println("## $name ##")

    val answers = readAnswers(name)

    val p1 = part1()
    val p1time = roundTime(beginTotal)
    val p1correct = p1 == answers.first
    val beginPart = System.nanoTime()
    val p2 = part2()
    val p2time = roundTime(beginPart)
    val totalTime = roundTime(beginTotal)

    println("Part 1: ${testSymbol(p1correct)}$p1 ($p1time)")
    println()
    if(!p1correct) {
        System.err.println("Part 1 Incorrect, Expected: ${answers.first}, Received: $p1")
    }
    val p2correct = p2 == answers.second
    println("Part 2: ${testSymbol(p2correct)}$p2 ($p2time)")
    println()
    if(!p2correct) {
        System.err.println("Part 2 Incorrect, Expected: ${answers.second}, Received: $p2")
    }

    println("Total time: $totalTime")
    println()
}

fun roundTime(begin: Long): String {
    var totalTime = Math.round((System.nanoTime()-begin)/1000.0)/1000.0
    var unit = "ms"
    if(totalTime > 1000) {
        totalTime = Math.round(totalTime)/1000.0
        unit = "s"
    }
    return "$totalTime$unit"
}

fun List<Int>.longProduct() = this.fold(1L) { acc, v -> acc * v }
fun List<Long>.product() = this.fold(1L) { acc, v -> acc * v }