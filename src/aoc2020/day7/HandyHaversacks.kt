package aoc2020.day7

import AoCDay
import aoc2020.readFromFile
import kotlin.collections.HashMap

class HandyHaversacks: AoCDay {
    private val bags = readFromFile("day7")
        .map(::parseBagLine)
        .toMap()

    override fun part1(): String {
        return findContaining("shiny gold", bags).size.toString()
    }

    override fun part2(): String {
        return numContainedBy("shiny gold", bags).toString()
    }

    private fun parseBagLine(input: String): Pair<String, Map<String, Int>> {
        val splitInput = input.split(" contain ")
        val bagName = splitInput[0].removeSuffix(" bags")

        if(input.contains("no other")) {
            return Pair(bagName, HashMap())
        }

        val innerBags = HashMap<String, Int>()
        splitInput[1].split(", ").forEach {
            val splitInnerBag = it.split(" ");
            val innerBagName = splitInnerBag[1] + " " + splitInnerBag[2]
            innerBags[innerBagName] = splitInnerBag[0].toInt()
        }
        return Pair(bagName, innerBags)
    }

    private fun findContaining(target: String, bags: Map<String, Map<String, Int>>): Set<String> {
        return bags
            .filter {(_, containing) -> containing.contains(target) }
            .flatMap {(bag, _) ->
                hashSetOf(bag).union(findContaining(bag, bags))
            }
            .toSet()
    }

    private fun numContainedBy(target: String, bags: Map<String, Map<String, Int>>): Int {
        return bags[target]!!
            .map {(bag, count) ->
                count * (1+ numContainedBy(bag, bags))
            }
            .sum()
    }
}

