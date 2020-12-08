package day8

import java.io.File
import java.util.*

fun main() {
    val program = Scanner(File("./src/day8/in.txt"))
        .useDelimiter("\n")
        .asSequence()
        .map(String::trim)
        .map {
            it.split(" ")
        }
        .map {
            Pair(it[0], it[1].toInt())
        }
        .toList()

    runProgram(program)
}

private fun runProgram(program: List<Pair<String,Int>>) {
    val prevInstr = HashSet<Int>()
    var acc = 0
    var pc = 0

    while(pc != program.size) {
        if(prevInstr.contains(pc)) {
            println(acc)
            return
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
}