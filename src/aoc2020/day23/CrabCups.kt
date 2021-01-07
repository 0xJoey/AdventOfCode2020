package aoc2020.day23

import AoCDay
import aoc2020.longProduct
import aoc2020.readFromFile

class CrabCups: AoCDay {

    private val cupOrder = readFromFile("day23")
        .first()
        .map {
            "$it".toInt()-1
        }

    private class Cup(val label: Int, var next: Cup? = null) {
        fun takeNext(n: Int): List<Cup> {
            return when {
                this.next == null   -> listOf()
                n == 1              -> listOf(this.next!!)
                else                -> listOf(this.next!!) + this.next!!.takeNext(n-1)
            }
        }
        override fun toString() = "($label->${next?.label ?: "x"})"
    }

    override fun part1(): String {
        val cupArray = createCupArray(cupOrder)
        playCups(cupArray, 100)
        return cupArray[0]
            .takeNext(8)
            .map {
                it.label+1
            }
            .joinToString("")
    }

    override fun part2(): String {
        val cupArray = createCupArray(cupOrder, 1_000_000)
        playCups(cupArray, 10_000_000)
        return cupArray[0]
            .takeNext(2)
            .map {
                it.label+1
            }
            .longProduct()
            .toString()
    }

    private fun createCupArray(cupOrder: List<Int>, size: Int = cupOrder.size): Array<Cup> {
        val cupArray = Array(size) { i -> Cup(i) }
        cupArray.forEachIndexed { i, cup ->
            if(i in 0..cupOrder.lastIndex) {
                val cupInOrder = (cupOrder.indexOf(cup.label) + 1) % cupOrder.size
                cup.next = cupArray[cupOrder[cupInOrder]]
            } else {
                cup.next = cupArray[(i + 1) % size]
            }
        }
        if(size > cupOrder.size) {
            cupArray.last().next = cupArray[cupOrder.first()]
            cupArray[cupOrder.last()].next = cupArray[cupOrder.size]
        }
        return cupArray
    }

    private fun playCups(cupArray: Array<Cup>, moves: Int) {
        var curCup = cupArray[cupOrder.first()]
        repeat(moves) {
            val pick1 = curCup.next!!
            val pick2 = pick1.next!!
            val pick3 = pick2.next!!

            var destinationIndex = curCup.label
            do {
                destinationIndex = (destinationIndex + cupArray.size - 1) % cupArray.size
            } while(pick1.label == destinationIndex || pick2.label == destinationIndex || pick3.label == destinationIndex)

            val destinationCup = cupArray[destinationIndex]
            val tmp = destinationCup.next
            destinationCup.next = pick1
            curCup.next = pick3.next
            pick3.next = tmp

            curCup = curCup.next!!
        }
    }
}