package day6

import java.io.File
import java.util.*

fun day6a(): String {
    return Scanner(File("./src/day6/in.txt"))
        .useDelimiter("\n\n")
        .asSequence()
        .map {
            it
                .split("\n")
                .flatMap {
                    line -> line
                        .toCharArray()
                        .toSet()
                }.toSet().size
        }
        .sum()
        .toString()
}