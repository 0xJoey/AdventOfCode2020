package day14

import readFromFile

private var highMask = 0L
private val floatingPositions = ArrayList<Int>()
private var floatingClearMask = 0L
private val memory = HashMap<Long, Long>()

private val MASK_BITLENGTH = 36

fun day14b(): String {
    readFromFile("day14")
        .map {
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

private fun applyMask(newMask: String) {
    highMask = newMask.replace("X", "0").toLong(2)

    floatingPositions.clear()
    floatingClearMask = 0L
    newMask.withIndex().forEach {(idx, char) ->
        if(char == 'X') {
            floatingClearMask = floatingClearMask or (1L.shl((MASK_BITLENGTH-1)-idx))
            floatingPositions.add((MASK_BITLENGTH-1)-idx)
        }
    }
}

private fun writeMemory(addr: Long, input: Long) {
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