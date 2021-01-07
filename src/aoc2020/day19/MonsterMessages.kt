package aoc2020.day19

import AoCDay
import aoc2020.readFromFile

class MonsterMessages: AoCDay {
    sealed class Rule {
        class Terminal(var char: Char): Rule()
        class Reference(var rules: List<List<Int>>): Rule()
    }

    private val lines = readFromFile("day19")
    val messages = lines.takeLastWhile { it != "" }
    val grammar = lines.takeWhile { it != "" }.map {
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

    override fun part1(): String {
        return messages
            .map {
                parse(grammar, it)
            }
            .count { it }
            .toString()
    }

    override fun part2(): String {
        val newGrammar = grammar.map { (k, v) ->
                when(k) {
                    8 -> k to Rule.Reference(listOf(listOf(42),listOf(42,8)))
                    11 -> k to Rule.Reference(listOf(listOf(42, 31), listOf(42, 11, 31)))
                    else -> k to v
                }
            }
            .toMap()
        return messages
            .map {
                parse(newGrammar, it)
            }
            .count { it }
            .toString()
    }

    private fun parse(grammar: Map<Int, Rule>, str: String, rules: List<Int> = listOf(0)): Boolean {
        if(rules.isEmpty()) return str.isBlank()

        return when(val rule = grammar[rules.first()]!!) {
            is Rule.Terminal -> str.startsWith(rule.char) && parse(grammar, str.drop(1), rules.drop(1))
            is Rule.Reference -> rule.rules.firstOrNull {  r ->
                parse(grammar, str, r + rules.drop(1))
            } != null
        }
    }
}