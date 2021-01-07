package aoc2020.day16

import AoCDay
import aoc2020.longProduct
import aoc2020.readFromFile

class TicketTranslation: AoCDay {
    private val lines = readFromFile("day16")
    private val ranges = lines.subList(0, lines.indexOf(""))
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
    private val otherTickets = lines.subList(lines.indexOf("nearby tickets:")+1, lines.size)
        .map {
            it.split(",").map(String::toInt)
        }


    override fun part1(): String {
        return otherTickets
            .flatMap { t ->
                t.filter { value ->
                    ranges
                        .flatMap{
                            it.second
                        }
                        .filter { range ->
                            value !in range
                        }
                        .size == ranges.size*2
                }
            }
            .sum()
            .toString()
    }

    override fun part2(): String {
        val myTicket = lines[lines.indexOf("your ticket:")+1].split(",").map(String::toInt)

        val checkedTickets = otherTickets
            .map { t ->
                t.map { v ->
                    ranges
                        .map { (name, subRanges) ->
                            if(subRanges.any { v in it }) {
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

        val mappedCategories = (0 until myTicket.size)
            .map {i ->
                checkedTickets
                    .map {
                        it[i]
                    }
                    .reduceRight { v, acc ->
                        acc.intersect(v).toList()
                    }
            }
            .withIndex()
            .sortedBy {(_, it) ->
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
            .map(Pair<Int, String>::second)

        return myTicket
            .filterIndexed { i, _ ->
                mappedCategories[i].startsWith("departure")
            }
            .longProduct()
            .toString()
    }

}