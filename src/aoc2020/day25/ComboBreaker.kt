package aoc2020.day25

import AoCDay
import aoc2020.readFromFile

class ComboBreaker: AoCDay {
    data class PublicKey(val card: Long, val door: Long)

    val lines = readFromFile("day25")

    val publicKeys = PublicKey(lines[0].toLong(), lines[1].toLong())

    override fun part1(): String {
        var encryptionKey = publicKeys.door
        repeat(findLoopNumber(publicKeys.card)) {
            encryptionKey = transform(encryptionKey, publicKeys.door)
        }
        return encryptionKey.toString()
    }

    override fun part2(): String {
        return "Free!"
    }

    private fun findLoopNumber(publicKey: Long): Int {
        var transformedSubject = 7L
        var loopNumber = 0
        while(publicKey != transformedSubject) {
            transformedSubject = transform(transformedSubject)
            loopNumber++
        }
        return loopNumber
    }

    private fun transform(n: Long, subject: Long = 7L): Long {
        return (n * subject) % DIVIDER
    }

    companion object {
        const val DIVIDER = 20201227L
    }
}