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

    var i = 0
    while(true) {
        val ret = runProgram(program, i)
        if(ret.first == 0) {
            println(ret.second)
            System.exit(0)
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