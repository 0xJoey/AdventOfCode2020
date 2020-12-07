package day7

import java.io.File
import java.util.*
import kotlin.collections.HashMap

private val allBags = HashMap<String, HashMap<String, Int>>()

fun main() {
    Scanner(File("./src/day7/in.txt"))
        .useDelimiter("\n")
        .asSequence()
        .map(String::trim)
        .map(::parseBagLine)
        .forEach { allBags[it.first] = it.second }

    println(numContainedBy("shiny gold", allBags))
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

private fun numContainedBy(target: String, bags: HashMap<String, HashMap<String, Int>>): Int {
    return bags[target]!!
        .map {(bag, count) ->
            count * (1+ numContainedBy(bag, bags))
        }
        .sum()
}