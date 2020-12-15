import day1.*
import day2.*
import day3.*
import day4.*
import day5.*
import day6.*
import day7.*
import day8.*
import day9.*
import day10.*
import day11.*
import day12.*
import day13.*
import day14.*
import day15.*
import helpers.Companion.testSymbol
import java.io.File

class helpers {
    companion object {
        var testSymbol: (Boolean) -> String = {bool ->
            if(bool) "✔️" else "❌"
        }
    }
}



fun readFromFile(day: String): List<String> {
    return File("./src/$day/in.txt")
        .readLines()
        .map(String::trim)
}

fun readAnswers(day: String): Pair<String, String> {
    val answers = File("./src/$day/out.txt")
        .readLines()
        .map(String::trim)
    return Pair(answers[0], answers[1])
}


fun runTests() {
    val beginAllDays = System.nanoTime()
    runDay("day1", ::day1a, ::day1b)
    runDay("day2", ::day2a, ::day2b)
    runDay("day3", ::day3a, ::day3b)
    runDay("day4", ::day4a, ::day4b)
    runDay("day5", ::day5a, ::day5b)
    runDay("day6", ::day6a, ::day6b)
    runDay("day7", ::day7a, ::day7b)
    runDay("day8", ::day8a, ::day8b)
    runDay("day9", ::day9a, ::day9b)
    runDay("day10", ::day10a, ::day10b)
    runDay("day11", ::day11a, ::day11b)
    runDay("day12", ::day12a, ::day12b)
    runDay("day13", ::day13a, ::day13b)
    runDay("day14", ::day14a, ::day14b)
    runDay("day15", ::day15a, ::day15b)
    //runDay("day16", ::day16a, ::day16b)
    //runDay("day17", ::day17a, ::day17b)
    //runDay("day18", ::day18a, ::day18b)
    //runDay("day19", ::day19a, ::day19b)
    //runDay("day20", ::day20a, ::day20b)
    //runDay("day21", ::day21a, ::day21b)
    //runDay("day22", ::day22a, ::day22b)
    //runDay("day23", ::day23a, ::day23b)
    //runDay("day24", ::day24a, ::day24b)
    //runDay("day25", ::day25a, ::day25b)

    println("## Summary ##")
    println("All days total: ${roundTime(beginAllDays)}")
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