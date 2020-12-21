package day19

import readFromFile

fun main() {
    println(day19a())
}

fun day19a(): String {
    val lines = readFromFile("day19")
    val indexOfEmpty = lines.indexOf("")

    val rules = lines.subList(0, indexOfEmpty)
    val messages = lines.subList(indexOfEmpty+1, lines.size)

    val grammar = rules.map {
        val split = it.split(":").map(String::trim)
        val key = split[0].toInt()

        if(split[1].startsWith('"')) {
            Pair(key, Rule.Terminal(split[1][1]))
        } else {
            val references = split[1]
                .trim()
                .split("|")
                .map { str -> str.trim() }
                .map { r ->
                    r.split(" ").map(String::toInt)
                }
            Pair(key, Rule.Reference(references))
        }
    }.toMap()
    return lines.subList(indexOfEmpty+1, lines.size)
        .map {
            parse(grammar, it)
        }
        .count { it }
        .toString()
}

private fun parse(grammar: Map<Int, Rule>, str: String, rules: List<Int> = listOf(0)): Boolean {
    if (rules.isEmpty()) return str.isBlank()

    return when (val rule = grammar[rules.first()]) {
        is Rule.Terminal -> str.startsWith(rule.char) && parse(grammar, str.drop(1), rules.drop(1))
        is Rule.Reference -> rule.rules.firstOrNull { r ->
            parse(grammar, str, r + rules.drop(1))
        } != null
        else -> false
    }
}