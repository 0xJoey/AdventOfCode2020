package day2

import readFromFile

fun day2b(): String {
    return readFromFile("day2")
        .filter(::verify)
        .count()
        .toString()
}

private fun verify(input: String): Boolean {
    var step = input.split("-")

    val pos1 = step[0].toInt()-1
    step = step[1].split(" ")
    val pos2 = step[0].toInt()-1
    val key = step[1][0]
    val password = step[2]


    return (password[pos1] == key).xor((password[pos2] == key))
}