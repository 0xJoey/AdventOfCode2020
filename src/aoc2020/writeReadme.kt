package aoc2020

import java.io.File
import java.io.PrintStream

fun main() {
    helpers.testSymbol = { bool ->
        if(bool) ":heavy_check_mark:" else ":x:"
    }
    System.setOut(PrintStream(File("./readme.md")))
    runTests()
}