package day7

import readFromFile
import kotlin.collections.HashMap

private val allBags = HashMap<String, HashMap<String, Int>>()

fun day7a(): String {
    readFromFile("day7")
        .map(::parseBagLine)
        .forEach { allBags[it.first] = it.second }

    return findContaining("shiny gold", allBags).size.toString()
}

private fun parseBagLine(input: String): Pair<String, HashMap<String, Int>> {
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

private fun findContaining(target: String, bags: HashMap<String, HashMap<String, Int>>): Set<String> {
    return bags
        .filter {(_, containing) -> containing.contains(target) }
        .flatMap {(bag, _) ->
            hashSetOf(bag).union(findContaining(bag, bags))
        }
        .toSet()
}