package aoc2020.day14

import AoCDay
import aoc2020.readFromFile

class DockingData: AoCDay {

    private val lines = readFromFile("day14")

    private var highMask = 0L
    private var lowMask = 0L
    private val floatingPositions = ArrayList<Int>()
    private var floatingClearMask = 0L
    private val memory = HashMap<Long, Long>()

    override fun part1(): String {
        highMask = 0L
        lowMask = 0L
        memory.clear()
        lines.map {
                when(it.substring(0,3)) {
                    "mas" -> applyMask(it.split("=")[1].trim())
                    "mem" -> {
                        val brackets = (it.indexOf("[")+1 until it.indexOf("]"))
                        val addr = it.substring(brackets).toLong()
                        writeMemory(addr, it.split("=")[1].trim().toLong())
                    }
                }
            }
        return memory.values.sum().toString()
    }

    override fun part2(): String {
        highMask = 0L
        lowMask = 0L
        memory.clear()
        lines.map {
                when(it.substring(0,3)) {
                    "mas" -> applyFloatingMask(it.split("=")[1].trim())
                    "mem" -> {
                        val brackets = (it.indexOf("[")+1 until it.indexOf("]"))
                        val addr = it.substring(brackets).toLong()
                        writeFloatingMemory(addr, it.split("=")[1].trim().toLong())
                    }
                }
            }
        return memory.values.sum().toString()
    }

    private fun writeMemory(addr: Long, input: Long) {
        //println("Writing ${input.toLong()} to $addr")
        var masked = input.toLong()
        masked = masked or highMask
        masked = masked and lowMask.inv()
        //println("After mask: $masked to $addr")
        memory[addr] = masked
    }

    private fun writeFloatingMemory(addr: Long, input: Long) {
        val options = Math.pow(2.0, floatingPositions.size.toDouble()).toInt()
        for(i in 0 until options) {
            var mask = 0L
            for(j in 0 until floatingPositions.size) {
                val floating = if(i and (1.shl(j)) > 0) 1 else 0
                mask = mask or floating.toLong().shl(floatingPositions[j])
            }
            var newAddr = addr or highMask
            newAddr = newAddr and floatingClearMask.inv()
            newAddr = newAddr or mask
            memory[newAddr] = input
        }
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

    private fun applyFloatingMask(newMask: String) {
        highMask = newMask.replace("X", "0").toLong(2)

        floatingPositions.clear()
        floatingClearMask = 0L
        newMask.withIndex().forEach {(idx, char) ->
            if(char == 'X') {
                floatingClearMask = floatingClearMask or (1L.shl((MASK_BITLENGTH -1)-idx))
                floatingPositions.add((MASK_BITLENGTH -1)-idx)
            }
        }
    }

    companion object {
        private const val MASK_BITLENGTH = 36
    }

}