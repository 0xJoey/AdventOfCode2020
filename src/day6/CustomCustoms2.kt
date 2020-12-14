package day6

import java.io.File
import java.util.*

fun day6b(): String {
    return Scanner(File("./src/day6/in.txt"))
        .useDelimiter("\n\n")
        .asSequence()
        .map { it.split("\n") }
        .map { line -> line
            .filter { it.isNotEmpty() }
            .map{ it.toCharArray().toList() }
            .reduceRight{ v, acc -> acc.intersect(v).toList() }.size
            }
        .sum()
        .toString()
}


