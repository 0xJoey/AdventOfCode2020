package aoc2020.day8

import AoCDay
import aoc2020.readFromFile
import java.util.*

class HandheldHalting: AoCDay {
    private val program = readFromFile("day8")
        .map {
            it.split(" ")
        }
        .map {
            Pair(it[0], it[1].toInt())
        }
        .toList()

    override fun part1(): String {
        return runProgram(program, -1).second.toString()
    }

    override fun part2(): String {
        var i = 0
        while(true) {
            val ret = runProgram(program, i)
            if(ret.first == 0) {
                return ret.second.toString()
            }
            i++
        }
    }

    private fun runProgram(program: List<Pair<String,Int>>, target: Int): Pair<Int, Int> {
        val prevInstr = HashSet<Int>()
        var counter = 1
        var acc = 0
        var pc = 0

        while(pc != program.size) {
            if(prevInstr.contains(pc)) {
                return Pair(1, acc)
            }
            //println("${program[pc]} | pc:$pc | acc:$acc")
            when(program[pc].first) {
                "nop" -> {
                    counter++
                    if(counter == target) {
                        pc += program[pc].second
                        pc--
                    }
                }
                "acc" -> {
                    acc += program[pc].second
                }
                "jmp" -> {
                    counter++
                    if(counter != target) {
                        pc += program[pc].second
                        pc--
                    }
                }
            }
            prevInstr.add(pc)
            pc++
        }
        return Pair(0, acc)
    }

}