package day16

import readFromFile

fun main() {
    println(day16b())
}

fun day16b(): String {
    val lines = readFromFile("day16")

    val ranges = lines.subList(0, lines.indexOf(""))
        .map {
            val split = it.split(": ")
            Pair(split[0], split[1].split(" or "))
        }
        .map {(name, ranges) ->
            Pair(name, ranges
                .map {
                    it.split("-").map(String::toInt)
                }
                .map {
                    (it[0]..it[1])
                })
        }

    val ticket = lines[lines.indexOf("your ticket:")+1].split(",").map(String::toInt)

    val otherTickets = lines.subList(lines.indexOf("nearby tickets:")+1, lines.size)
        .map {
            it.split(",").map(String::toInt)
        }

    val checkedTickets = otherTickets
        .map { t ->
            t.map { v ->
                ranges
                    .map { (name, range) ->
                        if(range.any { v in it }) {
                            name
                        } else {
                            ""
                        }
                    }
                    .filter {
                        it != ""
                    }
            }
        }
        .filter {t ->
            !t.any {v ->
                v.isEmpty()
            }
        }

    val categoryNames = ranges.map{(n,_) -> n}.toMutableSet()

    val mappedCategories = (0 until ticket.size)
        .map {i ->
            checkedTickets
                .map {
                    it[i]
                }
        }
        .map {
            it.reduceRight { v, acc ->
                acc.intersect(v).toList()
            }
        }
        .withIndex()
        .sortedBy {(i, it) ->
            it.size
        }
        .map { (i, it) ->
            val isect = it.intersect(categoryNames)
            categoryNames.remove(isect.first())
            Pair(i, isect.first())
        }
        .sortedBy { (i, _) ->
            i
        }
        .map{ (_, it) ->
            it
        }
        .toList()

    val answer = ticket
        .mapIndexed { i, v ->
            Pair(mappedCategories[i], v)
        }
        .filter { (k,_) ->
            k.startsWith("departure")
        }
        .map {(_, v) ->
            v
        }
        .fold(1L) { v, acc ->
            v*acc
        }
    return answer.toString()
}