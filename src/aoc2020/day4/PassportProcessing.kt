package aoc2020.day4

import AoCDay
import aoc2020.readFromFile

class PassportProcessing: AoCDay {
    companion object {
        private val REQUIRED_FIELDS = arrayOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
        private val EYE_COLS = arrayOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    }

    private val passports = readFromFile("day4")
        .joinToString()
        .split(", , ")
        .map {
            it.replace(", ", " ").trim()
        }

    override fun part1(): String {
        return passports
            .filter {
                it
                    .split(" ")
                    .map {
                        it.split(":")[0]
                    }
                    .filter {
                        REQUIRED_FIELDS.contains(it)
                    }
                    .count() == REQUIRED_FIELDS.count()
            }
            .count()
            .toString()
    }

    override fun part2(): String {
        return passports
            .filter {
                it
                    .split(" ")
                    .map { it.split(":") }
                    .map {
                        Pair(it[0], it[1])
                    }
                    .filter {
                        REQUIRED_FIELDS.contains(it.first)
                    }
                    .filter {(key, value) ->
                        when(key) {
                            "byr" -> (1920..2002).contains(value.toInt())
                            "iyr" -> (2010..2020).contains(value.toInt())
                            "eyr" -> (2020..2030).contains(value.toInt())
                            "hgt" -> {
                                when {
                                    value.endsWith("cm") -> (150..193).contains(value.removeSuffix("cm").toInt())
                                    value.endsWith("in") -> (59..76).contains(value.removeSuffix("in").toInt())
                                    else -> false
                                }
                            }
                            "hcl" -> "#[0-9a-f]{6}".toRegex().matches(value)
                            "pid" -> "[0-9]{9}".toRegex().matches(value)
                            "ecl" -> EYE_COLS.contains(value)
                            else -> false
                        }
                    }
                    .count() == REQUIRED_FIELDS.count()
            }
            .count()
            .toString()
    }

}