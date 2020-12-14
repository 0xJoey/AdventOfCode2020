package day3

import readFromFile

fun day3a(): String {
    return readFromFile("day3")
        .mapIndexed { idx, it ->
            it.toCharArray()[(idx*3)%it.length]
        }
        .filter { it == '#' }
        .count()
        .toString()
}