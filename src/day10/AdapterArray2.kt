package day10

import readFromFile

val lut = HashMap<Int,Long>()

fun day10b(): String {
    val adapters = readFromFile("day10")
        .map(String::toInt)
    val endAdapter = adapters.max()!! + 3
    lut[endAdapter] = 1
    return (numTransmutations(adapters + endAdapter)(0)).toString()
}

private fun numTransmutations(adapters: List<Int>): (Int) -> Long {
    return { curAdapter ->
        if (lut.containsKey(curAdapter)) {
            //println("$curAdapter returns ${memo[curAdapter]!!}")
            lut[curAdapter]!!
        } else {
            val num = adapters
                .filter { it - curAdapter <= 3 }
                .filter { it - curAdapter > 0 }
                .map(numTransmutations(adapters))
                .sum()
            lut[curAdapter] = num
            num
        }
    }
}
