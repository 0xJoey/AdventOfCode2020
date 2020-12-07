package day6

import java.io.File
import java.util.*
import kotlin.collections.HashSet

fun main() {
    println(Scanner(File("./src/day6/in.txt"))
        .useDelimiter("\n\n")
        .asSequence()
        .map { it.split("\n") }
        .map { it
            .filter { !it.isEmpty() }
            .map{ it.toCharArray().toList() }
            .reduceRight{ it, acc -> acc.intersect(it).toList() }.size
            }
        .sum())
}
