package day4

import readFromFile

private val REQUIRED_FIELDS = arrayOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

fun day4a(): String {
    return readFromFile("day4")
        .joinToString()
        .split(", , ")
        .map {
            it.replace(", ", " ").trim()
        }
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