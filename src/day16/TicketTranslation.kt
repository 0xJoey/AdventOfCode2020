package day16

import readFromFile

fun main() {
    println(day16a())
}

fun day16a(): String {
    val lines = readFromFile("day16")

    val ranges = lines.subList(0, lines.indexOf(""))
        .map {
            it.split(": ")[1].split(" or ")
        }
        .map {ranges ->
            ranges
                .map {
                    it.split("-").map(String::toInt)
                }
                .map {
                    (it[0]..it[1])
                }
        }

    val otherTickets = lines.subList(lines.indexOf("nearby tickets:")+1, lines.size)
        .map {
            it.split(",").map(String::toInt)
        }

    // Get invalids
    val invalids = otherTickets
        .map {t ->
            t.filter {value ->
                val trueRanges = ranges
                    .flatten()
                    .filter { range ->
                        value !in range
                    }
                trueRanges.size == ranges.size*2
            }
        }
        .flatten()
    return invalids.sum().toString()
}