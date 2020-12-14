package day5

import readFromFile

fun day5b(): String {
    val ids = readFromFile("day5")
        .map{calcId(it)}
    return findMissing(ids).toString()
}

private fun calcId(str: String): Int {
    val row = str.substring((0..6))
    val col = str.substring((7..9))
    val rowNo = strToBin(row, Pair('F', 'B'))
    val colNo = strToBin(col, Pair('L', 'R'))
    return rowNo * 8 + colNo
}

private fun strToBin(str: String, symbols: Pair<Char,Char>): Int {
    var value = 0
    for(char in str.toCharArray()) {
        value = value.shl(1)
        if(char == symbols.second) {
            value += 1
        }
    }
    return value
}

private fun findMissing(list: List<Int>): Int {
    val range = IntRange(list.min()!!, list.max()!!)
    for(i in range) {
        if(!list.contains(i)) {
            return i
        }
    }
    return -1
}