package day8

import readFromFile
import java.util.*

fun day8a(): String {
    val program = readFromFile("day8")
        .map {
            it.split(" ")
        }
        .map {
            Pair(it[0], it[1].toInt())
        }
        .toList()

    return runProgram(program)
}

private fun runProgram(program: List<Pair<String,Int>>): String {
    val prevInstr = HashSet<Int>()
    var acc = 0
    var pc = 0

    while(pc != program.size) {
        if(prevInstr.contains(pc)) {
            return acc.toString()
        }
        when(program[pc].first) {
            "nop" -> {
            }
            "acc" -> {
                acc += program[pc].second
            }
            "jmp" -> {
                pc += program[pc].second
                pc--
            }
        }
        prevInstr.add(pc)
        pc++
    }
    return "error"
}