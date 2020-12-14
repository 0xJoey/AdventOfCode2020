package day2

import readFromFile

fun day2a(): String {
    return readFromFile("day2")
        .filter(::verify)
        .count()
        .toString()
}

private fun verify(input: String): Boolean {
    var step = input.split("-")

    val min = step[0].toInt()
    step = step[1].split(" ")
    val max = step[0].toInt()
    val key = step[1][0]
    val password = step[2]

    val repeats = password.filter { it == key }.count()
    return repeats in (min..max)
}