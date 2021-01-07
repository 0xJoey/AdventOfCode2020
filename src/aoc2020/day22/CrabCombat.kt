package aoc2020.day22

import AoCDay
import java.io.File
import java.util.*

fun main() {
    CrabCombat2().part2()
}

class CrabCombat2: AoCDay {
    private val players = Scanner(File("./src/aoc2020/day22/in.txt"))
        .useDelimiter("\r\n\r\n")
        .asSequence()
        .map {
            it.split(System.lineSeparator()).drop(1).map(String::toInt)
        }
        .toList()

    override fun part1(): String {
        val queues = LinkedList(players[0]) to LinkedList(players[1])
        return playPart1(queues).toString()
    }

    override fun part2(): String {
        val queues = LinkedList(players[0]) to LinkedList(players[1])
        return playPart2(queues).second.toString()
    }

    private fun playPart1(players: Pair<Queue<Int>, Queue<Int>>): Int {
        val player1 = players.first
        val player2 = players.second
        while (player1.isNotEmpty() && player2.isNotEmpty()) {
            val card1 = player1.poll()
            val card2 = player2.poll()
            if (card1 > card2) {
                player1.offer(card1)
                player1.offer(card2)
            } else {
                player2.offer(card2)
                player2.offer(card1)
            }
        }
        return if (player1.isEmpty()) {
            var output = 0
            for (i in player2.size downTo 1) output += i * player2.poll()
            output
        } else {
            var output = 0
            for (i in player1.size downTo 1) output += i * player1.poll()
            output
        }
    }

    private fun playPart2(players: Pair<Queue<Int>, Queue<Int>>, recursion: Boolean = false): Pair<Boolean, Int> {
        val player1 = players.first
        val player2 = players.second

        val previousStates = mutableSetOf<Pair<Queue<Int>, Queue<Int>>>()
        var winner = true

        if(recursion) {
            if(player1.max()!! > player2.max()!!) {
                return true to 0
            }
        }

        while (true) {
            if (player1 to player2 in previousStates) {
                break
            }
            previousStates.add(player1 to player2)
            if (player1.isEmpty() || player2.isEmpty()) {
                winner = player2.isEmpty()
                break
            }
            val card1 = player1.poll()
            val card2 = player2.poll()
            if (card1 > player1.size || card2 > player2.size) {
                if (card1 > card2) {
                    player1.offer(card1)
                    player1.offer(card2)
                } else {
                    player2.offer(card2)
                    player2.offer(card1)
                }
            } else {
                val newPlayer1 = LinkedList(player1.take(card1))
                val newPlayer2 = LinkedList(player2.take(card2))
                LinkedList<Int>(listOf(1,2,3))
                if (playPart2(newPlayer1 to newPlayer2, recursion).first) {
                    player1.offer(card1)
                    player1.offer(card2)
                } else {
                    player2.offer(card2)
                    player2.offer(card1)
                }
            }
        }
        var output = 0
        if (! winner) for (i in player2.size downTo 1) output += i * player2.poll()
        else for (i in player1.size downTo 1) output += i * player1.poll()
        return winner to output
    }
}