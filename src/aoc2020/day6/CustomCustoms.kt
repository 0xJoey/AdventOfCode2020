package aoc2020.day6

import AoCDay
import java.io.File
import java.util.*

class CustomCustoms: AoCDay {
    private val lines = Scanner(File("./src/aoc2020/day6/in.txt"))
        .useDelimiter("\n\n")
        .asSequence()
        .toList()

    override fun part1(): String {
        return lines
            .map {
                it
                    .split("\n")
                    .map(String::toSet)
                    .reduceRight { acc, s -> acc + s }
                    .size
            }
            .sum()
            .toString()
    }

    override fun part2(): String {
        return lines
            .map { it.split("\n") }
            .map { line -> line
                .filter { it.isNotEmpty() }
                .map{ it.toCharArray().toList() }
                .reduceRight{ v, acc -> acc.intersect(v).toList() }.size
            }
            .sum()
            .toString()
    }
}