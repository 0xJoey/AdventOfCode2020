package day14

import readFromFile

private var highMask = 0L
private var lowMask = 0L
private val memory = HashMap<Int, Long>()

fun day14a(): String {
    readFromFile("day14")
        .map {
            when(it.substring(0,3)) {
                "mas" -> applyMask(it.split("=")[1].trim())
                "mem" -> {
                    val brackets = (it.indexOf("[")+1 until it.indexOf("]"))
                    val addr = it.substring(brackets).toInt()
                    writeMemory(addr, it.split("=")[1].trim())
                }
            }
        }
    return memory.values.sum().toString()
}

private fun applyMask(newMask: String) {
    highMask = newMask.replace("X", "0").toLong(2)
    lowMask = newMask.map {
        if(it == 'X') {
            '0'
        } else if(it == '0') {
            '1'
        } else {
            '0'
        }
    }.joinToString("").toLong(2)
}

private fun writeMemory(addr: Int, input: String) {
    //println("Writing ${input.toLong()} to $addr")
    var masked = input.toLong()
    masked = masked or highMask
    masked = masked and lowMask.inv()
    //println("After mask: $masked to $addr")
    memory[addr] = masked
}